package dwz.common.util.excel;
/* ==================================================================== 
   Licensed to the Apache Software Foundation (ASF) under one or more 
   contributor license agreements.  See the NOTICE file distributed with 
   this work for additional information regarding copyright ownership. 
   The ASF licenses this file to You under the Apache License, Version 2.0 
   (the "License"); you may not use this file except in compliance with 
   the License.  You may obtain a copy of the License at 
 
       http://www.apache.org/licenses/LICENSE-2.0 
 
   Unless required by applicable law or agreed to in writing, software 
   distributed under the License is distributed on an "AS IS" BASIS, 
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
   See the License for the specific language governing permissions and 
   limitations under the License. 
==================================================================== */  
  
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
  
/** 
 * 使用CVS模式解决XLSX文件，可以有效解决用户模式内存溢出的问题 
 * 该模式是POI官方推荐的读取大数据的模式，在用户模式下，数据量较大、Sheet较多、或者是有很多无用的空行的情况 
 * ，容易出现内存溢出,用户模式读取Excel的典型代码如下： FileInputStream file=new 
 * FileInputStream("c:\\test.xlsx"); Workbook wb=new XSSFWorkbook(file); 
 *  
 *  
 * @author 山人 
 */  
public class Xlsx2CsvReader {
    
    private static Logger logger = LoggerFactory.getLogger(Xlsx2CsvReader.class);
    
    private OPCPackage xlsxPackage;
    private int minColumns;
    private PrintStream output;
    private String sheetName;
    private boolean isReadAllSheet;
    
    /** 
     * The type of the data value is indicated by an attribute on the cell. The 
     * value is usually in a "v" element within the cell. 
     */  
    enum XssfDataType {
        BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER,  
    }  
    
    /** 
     * Creates a new XLSX -> CSV converter 
     *  
     * @param pkg 
     *            The XLSX package to process 
     * @param output 
     *            The PrintStream to output the CSV to 
     * @param minColumns 
     *            The minimum number of columns to output, or -1 for no minimum 
     * @param isReadAllSheet 是否允许读取所有sheet标签，默认只能读取第一个标签或指定名称的标签
     */  
    public Xlsx2CsvReader(OPCPackage pkg, String sheetName, int minColumns, boolean isReadAllSheet) {
        this.xlsxPackage = pkg; 
        this.sheetName = sheetName;
        this.minColumns = minColumns;
        this.isReadAllSheet = isReadAllSheet;
    }
    
    /** 
     * Creates a new XLSX -> CSV converter 
     *  
     * @param pkg 
     *            The XLSX package to process 
     * * @param sheetName 
     *            The sheetName 
     * @param minColumns 
     *            The minimum number of columns to output, or -1 for no minimum 
     */  
    public Xlsx2CsvReader(OPCPackage pkg, String sheetName, int minColumns) {
        this(pkg, sheetName, minColumns, true);
    }
    
    /** 
     * Creates a new XLSX -> CSV converter 
     *  
     * @param pkg 
     *            The XLSX package to process 
     * @param output 
     *            The PrintStream to output the CSV to 
     * @param minColumns 
     *            The minimum number of columns to output, or -1 for no minimum 
     */  
    public Xlsx2CsvReader(OPCPackage pkg, int minColumns) {
        this(pkg, null, minColumns, false);
    }
    
    /**
     * 通过文件路径实例化对象
     * @param filePath 文件路径
     * @param sheetName  sheet名称
     * @param minColumns 每行读取的列数
     * @throws InvalidFormatException
     */
    public Xlsx2CsvReader(String filePath, String sheetName, int minColumns, boolean isReadAllSheet) throws InvalidFormatException {
        this(OPCPackage.open(filePath, PackageAccess.READ), sheetName, minColumns, isReadAllSheet);
    }
    
    /**
     * 通过文件路径实例化对象 默认读取第一个sheet标签
     * @param filePath 文件路径
     * @param minColumns 每行读取的列数
     * @throws InvalidFormatException
     */
    public Xlsx2CsvReader(String filePath, int minColumns) throws InvalidFormatException {
        this(OPCPackage.open(filePath, PackageAccess.READ),  null, minColumns);
    }
    
    /**
     * 通过输入流实例化对象
     * @param inputSteam 输入文件流
     * @param sheetName  sheet名称
     * @param minColumns 每行读取的列数
     * @throws InvalidFormatException
     * @throws IOException
     */
    public Xlsx2CsvReader(InputStream inputSteam, String sheetName, int minColumns, boolean isReadAllSheet) throws InvalidFormatException, IOException {
        this(OPCPackage.open(inputSteam), sheetName, minColumns, isReadAllSheet);
    }
    /**
     * 通过输入流实例化对象
     * @param inputSteam 输入文件流
     * @param sheetName  sheet名称
     * @param minColumns 每行读取的列数
     * @throws InvalidFormatException
     * @throws IOException
     */
    public Xlsx2CsvReader(InputStream inputSteam, String sheetName, int minColumns) throws InvalidFormatException, IOException {
        this(inputSteam, sheetName, minColumns, false);
    }
    /**
     * 通过输入流实例化对象
     * @param inputSteam 输入文件流
     * @param minColumns 每行读取的列数
     * @throws InvalidFormatException
     * @throws IOException
     */
    public Xlsx2CsvReader(InputStream inputSteam,  int minColumns) throws InvalidFormatException, IOException {
        this(inputSteam, null, minColumns);
    }
    
    /** 
     * 初始化这个处理程序 将 
     *  
     * @throws IOException 
     * @throws OpenXML4JException 
     * @throws ParserConfigurationException 
     * @throws SAXException 
     */  
    public List<String[]> process() throws IOException, OpenXML4JException,  
            ParserConfigurationException, SAXException {
  
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
        List<String[]> list = new ArrayList<String[]>();
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        while (iter.hasNext()) {
            InputStream stream = iter.next();
            String tmpName = iter.getSheetName();
            
            if (this.sheetName == null) {
                list.addAll(processSheet(styles, strings, stream));
                stream.close();
                if (!isReadAllSheet) {
                    break;
                }
            } else if (this.sheetName.equals(tmpName)) {
                list.addAll(processSheet(styles, strings, stream));
                stream.close();
                break;
            }  
        }  
        return list;
    }
    
    /** 
     * Parses and shows the content of one sheet using the specified styles and 
     * shared-strings tables. 
     *  
     * @param styles 
     * @param strings 
     * @param sheetInputStream 
     */  
    public List<String[]> processSheet(StylesTable styles,  
            ReadOnlySharedStringsTable strings, InputStream sheetInputStream)  
            throws IOException, ParserConfigurationException, SAXException {

        XSSFSheetHandler handler = new XSSFSheetHandler(styles, strings, this.minColumns, this.output);
        
        InputSource sheetSource = new InputSource(sheetInputStream);
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        XMLReader sheetParser = saxParser.getXMLReader();
        
        sheetParser.setContentHandler(handler);
        sheetParser.parse(sheetSource);
        
        return handler.getRows();
    }

    /** 
     * 读取Excel 
     * 
     * @return 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     * @throws OpenXML4JException 
     * @throws IOException 
     */  
    public List<String[]> readExcel() throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
        return this.process(); 
    }
    /** 
     * 读取Excel 
     * 
     * @return 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     * @throws OpenXML4JException 
     * @throws IOException 
     */  
    public List<String[]> writeCsv(PrintStream output) throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
        this.output = output;
        return this.process(); 
    }
    /** 
     * 读取Excel 
     * 
     * @return 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     * @throws OpenXML4JException 
     * @throws IOException 
     */  
    public List<String[]> writeCsv(String destFile) throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
        this.output = new PrintStream(destFile);
        return this.process(); 
    }
    
    /** 
     * 使用xssf_sax_API处理Excel,请参考： http://poi.apache.org/spreadsheet/how-to.html#xssf_sax_api 
     * <p/> 
     * Also see Standard ECMA-376, 1st edition, part 4, pages 1928ff, at 
     * http://www.ecma-international.org/publications/standards/Ecma-376.htm 
     * <p/> 
     * A web-friendly version is http://openiso.org/Ecma/376/Part4 
     */  
    class XSSFSheetHandler extends DefaultHandler {
  
        /** 
         * Table with styles 
         */  
        private StylesTable stylesTable;
  
        /** 
         * Table with unique strings 
         */  
        private ReadOnlySharedStringsTable sharedStringsTable;
  
        /** 
         * Destination for data 
         */  
        private final PrintStream output;
  
        /** 
         * Number of columns to read starting with leftmost 
         */  
        private final int minColNum;
  
        // Set when V start element is seen  
        private boolean vIsOpen;
  
        // Set when cell start element is seen;
        // used when cell close element is seen.  
        private XssfDataType nextDataType;
  
        // Used to format numeric cell values.  
        private short formatIndex;
        private String formatString;
        private final DataFormatter formatter;
  
        private int thisColumn = -1;
        // The last column printed to the output stream  
        private int lastColumnNumber = -1;
  
        // Gathers characters as they are seen.  
        private StringBuffer value;
        private String[] record;
        private List<String[]> rows = new ArrayList<String[]>();
        private boolean isCellNull = false;
  
        /** 
         * Accepts objects needed while parsing. 
         *  
         * @param styles 
         *            Table of styles 
         * @param strings 
         *            Table of shared strings 
         * @param minColNum 
         *            Minimum number of columns to show 
         * @param target 
         *            Sink for output 
         */  
        public XSSFSheetHandler(StylesTable styles,  ReadOnlySharedStringsTable strings, int minColNum, PrintStream target) {
            this.stylesTable = styles;
            this.sharedStringsTable = strings;
            this.minColNum = minColNum;
            this.output = target==null ? System.out : target;
            this.value = new StringBuffer();
            this.nextDataType = XssfDataType.NUMBER;
            this.formatter = new DataFormatter();
            record = new String[this.minColNum];
            rows.clear();// 每次读取都清空行集合  
        } 
  
        /* 
         * (non-Javadoc) 
         *  
         * @see 
         * org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, 
         * java.lang.String, java.lang.String, org.xml.sax.Attributes) 
         */  
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        	
            if ("inlineStr".equals(qName) || "v".equals(qName)) {
                vIsOpen = true;
                // Clear contents cache  
                value.setLength(0);
            } else if ("c".equals(qName)) {// c => cell  
                // Get the cell reference  
                String r = attributes.getValue("r");
                int firstDigit = -1;
                for (int c = 0; c < r.length(); ++c) {
                    if (Character.isDigit(r.charAt(c))) {
                        firstDigit = c;
                        break;
                    }  
                }  
                thisColumn = nameToColumn(r.substring(0, firstDigit));
  
                // Set up defaults.  
                this.nextDataType = XssfDataType.NUMBER;
                this.formatIndex = -1;
                this.formatString = null;
                
                String cellType = attributes.getValue("t");
                String cellStyleStr = attributes.getValue("s");
                
                if ("b".equals(cellType)) nextDataType = XssfDataType.BOOL;
                else if ("e".equals(cellType)) nextDataType = XssfDataType.ERROR;
                else if ("inlineStr".equals(cellType)) nextDataType = XssfDataType.INLINESTR;
                else if ("s".equals(cellType)) nextDataType = XssfDataType.SSTINDEX;
                else if ("str".equals(cellType)) nextDataType = XssfDataType.FORMULA;
                else if (cellStyleStr != null) {
                    // It's a number, but almost certainly one  
                    // with a special style or format  
                    int styleIndex = Integer.parseInt(cellStyleStr);
                    XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
                    this.formatIndex = style.getDataFormat();
                    this.formatString = style.getDataFormatString();
                    if (this.formatString == null) this.formatString = BuiltinFormats.getBuiltinFormat(this.formatIndex);
                }
            }  else if ("row".equals(qName)) {
            	//System.out.println("name:" + qName + " index:" + attributes.getIndex(qName) 
            	//	+ " type:" + attributes.getType(qName)
            	//	+ " value:" + attributes.getValue(qName)
            	//);
            	
            	/*
                // Print out any missing commas if needed  
                if (minColumns > 0) {
                    // Columns are 0 based  
                    if (lastColumnNumber == -1) {
                        lastColumnNumber = 0;
                    } 
                    
                    if (isCellNull == false && record[0] != null && record[1] != null) { // 判断是否空行  
                        rows.add(record.clone());
                        isCellNull = false;
                        for (int i = 0; i < record.length; i++) {
                            record[i] = null;
                        }
                    }
                }  
                lastColumnNumber = -1;
                */
            }  
  
        }  
  
        /* 
         * (non-Javadoc) 
         *  
         * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, 
         * java.lang.String, java.lang.String) 
         */  
        @Override
        public void endElement(String uri, String localName, String name)  
                throws SAXException {
  
            String thisStr = null;
            
            // v => contents of a cell  
            if ("v".equals(name)) {
                // Process the value contents as required.  
                // Do now, as characters() may be called more than once  
                switch (nextDataType) {
  
                case BOOL:  
                    char first = value.charAt(0);
                    thisStr = first == '0' ? "FALSE" : "TRUE";
                    break;
  
                case ERROR:  
                    thisStr = "ERROR:" + value.toString();
                    break;
                    
                case FORMULA:  
                    // A formula could result in a string value,  
                    // so always add double-quote characters.  
                    thisStr = value.toString();
                    break;
                    
                case INLINESTR:  
                    XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
                    thisStr = rtsi.toString();
                    break;
                    
                case SSTINDEX:  
                    String sstIndex = value.toString();
                    try {
                        int idx = Integer.parseInt(sstIndex);
                        XSSFRichTextString rtss = new XSSFRichTextString(sharedStringsTable.getEntryAt(idx));
                        thisStr = rtss.toString();
                    } catch (NumberFormatException ex) {
                        output.println("Failed to parse SST index '" + sstIndex + "': " + ex.toString());
                    }  
                    break;
                    
                case NUMBER:  
                    String n = value.toString();
                    // 判断是否是日期格式  
                    if (DateUtil.isADateFormat(this.formatIndex, n)) {
                        Double d = Double.parseDouble(n);
                        Date date = DateUtil.getJavaDate(d);
                        thisStr = formateDateToString(date);
                    } else if (this.formatString != null)  
                        thisStr = formatter.formatRawCellContents( Double.parseDouble(n), this.formatIndex, this.formatString);
                    else  
                        thisStr = n;
                    break;
                    
                default:  
                    thisStr = "(TODO: Unexpected type: " + nextDataType + ")";
                    break;
                }  
  
                // Output after we've seen the string contents  
                // Emit commas for any fields that were missing on this row  
                if (lastColumnNumber == -1) {
                    lastColumnNumber = 0;
                }  
                //判断单元格的值是否为空  
                if (thisStr == null || "".equals(isCellNull)) {
                    isCellNull = true;// 设置单元格是否为空值  
                }  
                record[thisColumn] = thisStr;
                // Update column  
                if (thisColumn > -1)  
                    lastColumnNumber = thisColumn;
  
            } else if ("row".equals(name)) {
  
                // Print out any missing commas if needed  
                if (minColumns > 0) {
                    // Columns are 0 based  
                    if (lastColumnNumber == -1) {
                        lastColumnNumber = 0;
                    } 
                    
                    if (isCellNull == false && record[0] != null && record[1] != null) { // 判断是否空行  
                        rows.add(record.clone());
                        isCellNull = false;
                        for (int i = 0; i < record.length; i++) {
                            record[i] = null;
                        }  
                    }  
                }  
                lastColumnNumber = -1;
            }  
  
        }  
  
        public List<String[]> getRows() {
            return rows;
        }  
  
        public void setRows(List<String[]> rows) {
            this.rows = rows;
        }  
  
        /** 
         * Captures characters only if a suitable element is open. Originally 
         * was just "v"; extended for inlineStr also. 
         */  
        @Override
        public void characters(char[] ch, int start, int length)  
                throws SAXException {
            if (vIsOpen)  
                value.append(ch, start, length);
        }  
  
        /** 
         * Converts an Excel column name like "C" to a zero-based index. 
         *  
         * @param name 
         * @return Index corresponding to the specified name 
         */  
        private int nameToColumn(String name) {
            int column = -1;
            for (int i = 0; i < name.length(); ++i) {
                int c = name.charAt(i);
                column = (column + 1) * 26 + c - 'A';
            }  
            return column;
        }  
  
        private String formateDateToString(Date date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式化日期  
            return sdf.format(date);
        }
    }
    
    public static void main(String[] args) throws Exception {
        List<String[]> list = new Xlsx2CsvReader(new FileInputStream("d:/test.xlsx"), null, 5, true).readExcel();
        
        System.out.println(list.size());
        
        for (String[] record : list) {
            for (String cell : record) {
                logger.info(cell + "  ");
            }  
            System.out.println();
        }
        
    }  
  
}  