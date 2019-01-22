package dwz.common.util.excel;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import dwz.common.util.StringUtils;
import dwz.common.util.time.DateUtils;
import java.io.IOException;

/**
 * A XLS -> Excel操作辅助类
 * ensure it outputs all columns and rows.
 * 
 * @author lvlei 
 */
public class ExcelBundle {

    private final Logger logger = LoggerFactory.getLogger(ExcelBundle.class);
    
    public List<String[]> readExcel(InputStream input, String fileName, String sheetName, int minColumns) throws Exception{  
        List<String[]> list = null;
        if (!input.markSupported()) {
             input = new PushbackInputStream(input, 8);
        }
        if (POIFSFileSystem.hasPOIFSHeader(input)) {
            list = new Xls2CsvReader(input, sheetName, minColumns).readExcel();
        } else if (POIXMLDocument.hasOOXMLHeader(input)) {
            list = new Xlsx2CsvReader(input, sheetName, minColumns).readExcel();  
        } else {
            //获取文件后缀
            String fx = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            try {
                if (fx.equals("xls")) {
                    list = new Xls2CsvReader(input, sheetName, minColumns).readExcel();
                } else if (fx.equals("xlsx")) {
                    list = new Xlsx2CsvReader(input, sheetName, minColumns).readExcel();  
                } else {
                    logger.error("你的excel版本目前poi解析不了");
                    throw new Exception("你的excel版本目前poi解析不了");
                }
                
            } catch (Exception e) {
                throw new Exception("你的excel文件不是合法的文件或者目前poi解析不了");
            }
        }
        
        return list;  
    }
    
    public List<String[]> readExcel(String srcFilePath, String sheetName, int minColumns) throws Exception {  
        return readExcel(new FileInputStream(srcFilePath), srcFilePath, sheetName, minColumns);
    }

    public List<String[]> readExcel(File srcFile, String sheetName, int minColumns) throws Exception {  
        return readExcel(new FileInputStream(srcFile), srcFile.getName(), sheetName, minColumns);
    }
    
    public List<String[]> readExcel(MultipartFile multiFile, String sheetName, int minColumns) throws Exception {  
        return readExcel(multiFile.getInputStream(), multiFile.getOriginalFilename(), sheetName, minColumns);
    }

    public List<String[]> readExcel(MultipartFile multiFile, int minColumns) throws Exception {  
        return readExcel(multiFile,  null, minColumns);
    }
    
    public List<String[]> readExcel(MultipartHttpServletRequest multiRequest, String sheetName, int minColumns) throws Exception {
        List<String[]> list = new ArrayList<>();
        Iterator<String> iter = multiRequest.getFileNames();  
        while(iter.hasNext()){
            list.addAll(readExcel(multiRequest.getFile(iter.next()), sheetName, minColumns));
        }
        return list;
    }
    
    /**
     * Excel 文件下载
     * @param strings
     * @param fileName
     * @param request
     * @param response
     */
    public void downloadExcel(List<String[]> strings, String fileName, HttpServletRequest request, HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        session.setAttribute("state", null);
        
        try {
            // 生成提示信息，
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // 进行转码，使其支持中文文件名
            response.setHeader("content-disposition", "attachment;filename=" + StringUtils.encodeFilename(fileName + DateUtils.formatStandardDate(new Date()) + ".xlsx", request));
            //将数据写入输出流
            writeExcel(strings, response.getOutputStream());
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            session.setAttribute("state", "open");
        }
    }
    
    /**
     * Excel 文件下载
     * @param tempFilePath
     * @param fileName
     * @param heads
     * @param datalist
     * @param request
     * @param response
     */
    public void downloadExcel(String tempFilePath, String fileName, String[] heads, List<Map<Integer, Object>> datalist, HttpServletRequest request, HttpServletResponse response) {
    	
        HttpSession session = request.getSession();
        session.setAttribute("state", null);
        
        try {
            ExcelTemplateUtil excel = new ExcelTemplateUtil();
            excel.writeDateList(tempFilePath, heads, datalist, 0);
            // 生成提示信息，
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // 进行转码，使其支持中文文件名
            response.setHeader("content-disposition", "attachment;filename=" + StringUtils.encodeFilename(fileName + DateUtils.formatStandardDate(new Date()) + ".xlsx", request));
            // 写到输出流并移除资源
            excel.writeAndClose(tempFilePath, response.getOutputStream());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            session.setAttribute("state", "open");
        }
    }
    /**
     * 将数据写入文件流
     * 
     * @param strings
     * @param output
     */
    public void writeExcel(List<String[]> strings, OutputStream output) {
        try {

            SXSSFWorkbook workbook = new SXSSFWorkbook(500);
            workbook.setCompressTempFiles(true);
            // 产生工作表对象
            SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet("List");
            sheet.setDefaultColumnWidth(4000);
            //每列自动适应宽度
            for(int i = 0; i < strings.get(0).length; i++) {
                sheet.autoSizeColumn(i); //自动适应宽度
            }
            
            SXSSFRow row;
            SXSSFCell cell;
            
            for (int i=0; i < strings.size(); i++) {
                if (i==0) {
                    row = (SXSSFRow) sheet.createRow(0);// 创建一行
                    for (int j=0; j < strings.get(0).length; j++) {
                    	cell = (SXSSFCell) row.createCell(j);// 创建一列
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cell.setCellValue(strings.get(0)[j]);
                    }
                } else {
                    row = (SXSSFRow) sheet.createRow(i);// 创建一行
                    for (int j=0; j < strings.get(i).length; j++) {
                    	String value = strings.get(i)[j];
                    	if (!"".equals(value)) {
                            cell = (SXSSFCell) row.createCell(j);// 创建一列
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            cell.setCellValue(strings.get(i)[j]);
                    	}
                    }
                }
            }
            workbook.write(output);
            workbook.close();
            workbook.dispose();
            
            strings.clear();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}