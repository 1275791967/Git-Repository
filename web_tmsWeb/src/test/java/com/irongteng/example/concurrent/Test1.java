package com.irongteng.example.concurrent;

import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*poi组件中常用的类有
XSSFworkbook表示一个完整的excel表格，
XSSFsheet表示excel中的一个工作薄，
XSSFRow表示工作薄中的一行，
XSSFCell表示一个单元格 
*/


public class Test1 {
    public static void main(String[] args) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("www");
            XSSFRow row = sheet.createRow(0);
            XSSFCell cell = row.createCell(0);
            XSSFCell cell1 = row.createCell(1);
            cell.setCellValue("aaaaa");
            cell1.setCellValue("bbbbb");
            FileOutputStream os = null;
            os = new FileOutputStream("D:/AAAA.xlsx");
            workbook.write(os);
            os.flush();
            
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
