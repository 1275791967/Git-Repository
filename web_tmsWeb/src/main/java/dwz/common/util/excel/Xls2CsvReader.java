package dwz.common.util.excel;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RowRecord;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A XLS -> CSV processor, that uses the MissingRecordAware EventModel code to
 * ensure it outputs all columns and rows.
 * 
 * @author Nick Burch 
 */
public class Xls2CsvReader implements HSSFListener {
    
    private static Logger logger = LoggerFactory.getLogger(Xls2CsvReader.class);
    
    private int minColumns;
    private POIFSFileSystem fs;

    private int lastRowNum;
    private int lastColNum;

    /** Should we output the formula, or the value it has? */
    private boolean outputFormulaValues = true;

    /** For parsing Formulas */
    private SheetRecordCollectingListener sheetListener;
    private HSSFWorkbook workbook;
    
    private SSTRecord sstRecord;
    
    private FormatTrackingHSSFListener formatListener;
    
    private String sheetName = null;
    
    private BoundSheetRecord[] orderedBSRs;
    
    private List<BoundSheetRecord> sheetRecords = new ArrayList<BoundSheetRecord>();
    
    private List<String[]> strings = new ArrayList<String[]>(); //保存每行的字符串
    
    private String[] colStrings = null; //记录每行每列的参数
    
    //存储excel每行每列的值
    private Map<Integer,String[]> rowIndexsMap = new HashMap<Integer,String[]>();
        
    // For handling formulas with string results
    private int nextRow;
    
    private int nextColumn;
    
    private boolean nextStringRecord;
    
    
    public Xls2CsvReader() {
        
    }
    /**
     * Creates a new XLS -> CSV converter
     * 
     * @param fs
     *            The POIFSFileSystem to process
     * @param output
     *            The PrintStream to output the CSV to
     * @param minColumns
     *            The minimum number of columns to output, or -1 for no minimum
     */
    public Xls2CsvReader(POIFSFileSystem fs, String sheetName, int minColumns) {
        this.fs = fs;
        this.sheetName = sheetName;
        if (minColumns > 0) {
            this.minColumns = minColumns;
        } else {
            this.minColumns = -1;
        }
    }
    
    /**
     * Creates a new XLS -> CSV converter
     * 
     * @param fs
     *            The POIFSFileSystem to process
     * @param minColumns
     *            The minimum number of columns to output, or -1 for no minimum
     */
    public Xls2CsvReader(POIFSFileSystem fs, int minColumns) {
        this(fs, null, minColumns);
    }

    /**
     * Creates a new XLS -> CSV converter
     * 
     * @param fs
     *            The POIFSFileSystem to process
     */
    public Xls2CsvReader(POIFSFileSystem fs) {
        this(fs, null, -1);
    }
    

    
    /**
     * 
     * @param srcFile 源文件路径 包括完整文件名的源文件
     * @param dstFile  目标文件路径
     * @throws Exception
     */
    public Xls2CsvReader(String srcFile, String sheetName, int minColumns) throws Exception {
        this(new POIFSFileSystem(new FileInputStream(srcFile)),sheetName, minColumns);
    }

    /**
     * Creates a new XLS -> CSV converter
     * 
     * @param srcFile
     *            The file to process
     * @param minColumns
     *            The minimum number of columns to output, or -1 for no minimum
     * @throws Exception 
     */
    public Xls2CsvReader(String srcFile, int minColumns) throws Exception {
        this(srcFile, null, minColumns);
    }
    
    /**
     * 
     * @param srcFile 源文件路径 包括完整文件名的源文件
     * @throws Exception
     */
    public Xls2CsvReader(String srcFile) throws Exception {
        this(srcFile, null, -1);
    }
    /**
     * 
     * @param inputStream 输入文件流
     * @param minColumns  指定每行读取列数
     * @throws Exception  指定读取的sheet名称
     */
    
    public Xls2CsvReader(InputStream inputStream, String sheetName, int minColumns) throws Exception {
        this(new POIFSFileSystem(inputStream), sheetName, minColumns);
    }
    
    /**
     * 
     * @param inputStream 输入文件流
     * @param minColumns  指定每行读取列数
     * @throws Exception  指定读取的sheet名称
     */
    
    public Xls2CsvReader(InputStream inputStream, int minColumns) throws Exception {
        this(inputStream, null, minColumns);
    }

    /**
     * 
     * @param inputStream 输入文件流
     * @param minColumns  指定每行读取列数
     * @throws Exception  指定读取的sheet名称
     */
    public Xls2CsvReader(InputStream inputStream) throws Exception {
        this(inputStream, null, -1);
    }
    
    /**
     * Initiates the processing of the XLS file to CSV
     */
    public List<String[]> process() throws IOException {
        MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
        formatListener = new FormatTrackingHSSFListener(listener);
        
        HSSFEventFactory factory = new HSSFEventFactory();
        HSSFRequest request = new HSSFRequest();

        if (outputFormulaValues) {
            request.addListenerForAllRecords(formatListener);
        } else {
            sheetListener = new SheetRecordCollectingListener(formatListener);
            request.addListenerForAllRecords(sheetListener);
        }
        
        factory.processWorkbookEvents(request, fs);
        
        return strings;
    }

    /**
     * Main HSSFListener method, processes events, and outputs the CSV as the
     * file is processed.
     */
    @Override
    public void processRecord(Record record) {
        int thisRow = -1;
        int thisColumn = -1;
        String thisStr = null;

        switch (record.getSid()) {
        
        case BOFRecord.sid:  //1 文件类型标记
            BOFRecord br = (BOFRecord) record;
            if (br.getType() == BOFRecord.TYPE_WORKSHEET) {
                // Create sub workbook if required
                if (sheetListener != null && workbook == null) {
                    workbook = sheetListener.getStubHSSFWorkbook();
                }
                
                if (orderedBSRs == null) {
                    orderedBSRs = BoundSheetRecord.orderByBofPosition(sheetRecords);
                }
            }
            break;
        case BoundSheetRecord.sid:   //2sheet标记 
            BoundSheetRecord sheetRecord = (BoundSheetRecord)record;
            
            if (sheetName == null || sheetRecord.getSheetname().equalsIgnoreCase(sheetName)) { //只有指定sheetName或者不指定sheetName才能添加record记录
                sheetRecords.add(sheetRecord);
            }
            
            break;
        case SSTRecord.sid:  //3 
            sstRecord = (SSTRecord) record;
            break;
        case RowRecord.sid:   //4 Row标记
            RowRecord rowRecord = (RowRecord) record;
            thisRow = rowRecord.getRowNumber();
            
            if (minColumns == -1) {
                rowIndexsMap.put(rowRecord.getRowNumber(), new String[rowRecord.getLastCol() + 1]);
            } else {
                rowIndexsMap.put(rowRecord.getRowNumber(), new String[minColumns]);
            }
            
            break;
        case LabelSSTRecord.sid: //5 读取每列
            //sheet有记录时才能读取
            if (sheetRecords.size() > 0) {
                
                LabelSSTRecord lsrec = (LabelSSTRecord) record;
                
                thisRow = lsrec.getRow();
                thisColumn = lsrec.getColumn();
                
                if (sstRecord == null) {
                    thisStr = "(No SST Record, can't identify string)";
                } else {
                    thisStr = sstRecord.getString(lsrec.getSSTIndex()).toString();
                }
                //rowIndexsMap.get(thisRow)[thisColumn] = thisStr;
            }
            
            break;
        case BlankRecord.sid:
            BlankRecord brec = (BlankRecord) record;

            thisRow = brec.getRow();
            thisColumn = brec.getColumn();
            thisStr = "";
            break;
        case BoolErrRecord.sid:
            BoolErrRecord berec = (BoolErrRecord) record;

            thisRow = berec.getRow();
            thisColumn = berec.getColumn();
            thisStr = "";
            
            break;
        case FormulaRecord.sid:
            FormulaRecord frec = (FormulaRecord) record;
            
            thisRow = frec.getRow();
            thisColumn = frec.getColumn();

            if (outputFormulaValues) {
                if (Double.isNaN(frec.getValue())) {
                    // Formula result is a string
                    // This is stored in the next record
                    nextStringRecord = true;
                    nextRow = frec.getRow();
                    nextColumn = frec.getColumn();
                } else {
                    thisStr = formatListener.formatNumberDateCell(frec);
                }
            } else {
                thisStr = HSSFFormulaParser.toFormulaString(workbook, frec.getParsedExpression());
            }
            
            break;
        case StringRecord.sid:
            if (nextStringRecord) {
                // String for formula
                StringRecord srec = (StringRecord) record;
                thisStr = srec.getString();
                thisRow = nextRow;
                thisColumn = nextColumn;
                nextStringRecord = false;
            }
            
            break;
        case LabelRecord.sid:
            LabelRecord lrec = (LabelRecord) record;

            thisRow = lrec.getRow();
            thisColumn = lrec.getColumn();
            thisStr = lrec.getValue();
            
            break;
        case NoteRecord.sid:
            NoteRecord nrec = (NoteRecord) record;

            thisRow = nrec.getRow();
            thisColumn = nrec.getColumn();
            thisStr = "(TODO)";
            
            break;
        case NumberRecord.sid:
            NumberRecord numrec = (NumberRecord) record;

            thisRow = numrec.getRow();
            thisColumn = numrec.getColumn();

            // Format
            thisStr = formatListener.formatNumberDateCell(numrec);
            break;
        case RKRecord.sid:
            RKRecord rkrec = (RKRecord) record;
            
            thisRow = rkrec.getRow();
            thisColumn = rkrec.getColumn();
            thisStr = "(TODO)";
            break;
        default:
            break;
        }
        
        //sheet记录数是否大于0，否则不写入
        if (sheetRecords.size() > 0) {
            
            // Handle new row
            if (thisRow != -1 && thisRow != lastRowNum) {
                lastColNum = -1;
            }
            
            // Handle missing column
            if (record instanceof MissingCellDummyRecord) {
                MissingCellDummyRecord mc = (MissingCellDummyRecord) record;
                thisRow = mc.getRow();
                thisColumn = mc.getColumn();
                thisStr = "";
            }
            
            //提取每个cell的参数值
            if (thisStr != null) {
                rowIndexsMap.get(thisRow)[thisColumn] = thisStr;
            }
            
            // Update column and row count
            if (thisRow > -1)
                lastRowNum = thisRow;
            if (thisColumn > -1)
                lastColNum = thisColumn;
            //rowRecordTag = false;
            // Handle end of row
            if (record instanceof LastCellOfRowDummyRecord) {
                
                try {
                    LastCellOfRowDummyRecord lastCellRecord = (LastCellOfRowDummyRecord) record;
                    //将读取的一列列的参数值写到list列表
                    colStrings = rowIndexsMap.get(lastCellRecord.getRow());
                    
                    if (colStrings != null) { //用于排除某行空格的情况
                        //System.out.println("" + colStrings[0] + " " + colStrings[1] + " " + colStrings[2] + " " + colStrings[3] + " " + colStrings[4]);
                        strings.add(colStrings);
                    }
                    
                    if (minColumns > 0) {
                        // Columns are 0 based
                        if (lastColNum == -1) {
                            lastColNum = 0;
                        }
                    }
                    
                    // We're onto a new row
                    lastColNum = -1;
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
                
            }
        }
    }
    
    public List<String[]> readExcel() throws Exception{ 
        return this.process();  
    }
    
    public static void main(String[] args) throws Exception {
        List<String[]> strings = new Xls2CsvReader("d:/test2.xls", 5).readExcel();
        //List<String[]> strings = xls2csv.process();
        System.out.println("string size:" + strings.size());
       // System.out.println(Double.isNaN(0377));
    }
}