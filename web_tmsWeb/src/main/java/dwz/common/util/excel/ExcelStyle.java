package dwz.common.util.excel;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelStyle {

    private Workbook wb = null;
    private Sheet sheet = null;
    private Font font = null;
    private CellStyle style = null;

    public ExcelStyle(Workbook wb) {
        
        this.wb = wb;
        
        sheet = wb.createSheet();
        sheet.setDefaultColumnWidth(14);
        sheet.setDefaultRowHeight((short) 20);
        // 打印设置
        PrintSetup ps = sheet.getPrintSetup();
        ps.setPaperSize(PrintSetup.A4_PAPERSIZE); // 设置A4纸
        ps.setLandscape(true); // 将页面设置为横向打印模式
        // sheet.setHorizontallyCenter(true); // 设置打印页面为水平居中
        // sheet.setVerticallyCenter(true); // 设置打印页面为垂直居中
        // 冻结第一行和第二行
        sheet.createFreezePane(0, 1, 0, 1);
    }


    /**
     * 设置标题样式
     * 
     */
    public CellStyle titleStyle() {
        font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 18);
        
        style = wb.createCellStyle();
        style.setFont(font);
        // style.setBorderTop((short)1);
        // style.setBorderRight((short)1);
        style.setBorderBottom((short) 1);
        
        // style.setBorderLeft((short)1);

        style.setAlignment(CellStyle.ALIGN_CENTER);
        
        return style;

    }

    /**
     * 设置head样式
     * 
     */
    public CellStyle headStyle() {

        font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 11);
        
        style = wb.createCellStyle();
        style.setFont(font);
        style.setBorderTop((short) 1);
        style.setBorderRight((short) 1);
        style.setBorderBottom((short) 1);
        style.setBorderLeft((short) 1);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        return style;
    }

    /**
     * 设置body样式
     * 
     */
    public CellStyle bodyStyle() {
        font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 9);
        
        style = wb.createCellStyle();
        style.setFont(font);
        style.setBorderTop((short) 1);
        style.setBorderRight((short) 1);
        style.setBorderBottom((short) 1);
        style.setBorderLeft((short) 1);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return style;
    }
}