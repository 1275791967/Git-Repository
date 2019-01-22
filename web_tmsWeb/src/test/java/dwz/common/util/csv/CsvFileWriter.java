package dwz.common.util.csv;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
 
/**
 * @author ashraf
 * 
 */
public class CsvFileWriter {
     
    //CSV文件分隔符
    private static final String NEW_LINE_SEPARATOR = "\n";
     
    //CSV文件头
    private static final Object [] FILE_HEADER = {"用户名","密码","名称","年龄"};
     
    /**
     * 写CSV文件
     * 
     * @param fileName
     */
    public static void writeCsvFile(String fileName) {
        
        CSVPrinter csvFilePrinter = null;
        //创建 CSVFormat
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        try {
            
            //初始化 CSVPrinter
            csvFilePrinter = new CSVPrinter(new OutputStreamWriter(new FileOutputStream(fileName),"gbk"), csvFileFormat);
            //创建CSV文件头
            csvFilePrinter.printRecord(FILE_HEADER);
 
            // 用户对象放入List
            List<User> users = new ArrayList<User> ();
            users.add(new User("zhangsan", "123456", "张三", 25));
            users.add(new User("lisi", "123", "李四", 23));
            users.add(new User("wangwu", "456", "王五", 24));
            users.add(new User("zhaoliu", "zhaoliu", "赵六,test", 20));
            
            int index = 0;
            int lastIndex=0;
            
            for (int i=0; i<600000; i++) {
                index++;
                lastIndex ++;
                User user1 = new User("lvlei_" + i,"lvlei_Pwd_" + i,"名字_"+i, 21);
                
                users.add(user1);
                
                if(index>5000 || lastIndex>=600000) {
                    // 遍历List写入CSV
                    for (User user : users) {
                        List<String> userDataRecord = new ArrayList<String>();
                        userDataRecord.add(user.getUsername());
                        userDataRecord.add(user.getPassword());
                        userDataRecord.add(user.getName());
                        userDataRecord.add(String.valueOf(user.getAge()));
                        csvFilePrinter.printRecord(userDataRecord);
                    }
                    index = 0;
                    users.clear();
                    csvFilePrinter.flush();
                }
            }
            
            System.out.println("CSV文件创建成功~~~");
             
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (csvFilePrinter != null)csvFilePrinter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * @param args
     */
    public static void main(String[] args){
        writeCsvFile("d://users.csv");
    }
     
}