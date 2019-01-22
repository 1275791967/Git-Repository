package dwz.common.util;

import java.io.File;

import org.junit.Test;

public class FileCharsetDetectorTest {

    @Test
    public void testGuessFileEncoding() throws Exception {
         
         File file1 = new File("C:\\apps\\Hotpoint_755_你去死吧_2_201604251223.hp");
         System.out.println("文件编码:" + new FileCharsetDetector().guessFileEncoding(file1));
         
         File file2 = new File("C:\\apps\\uft8.txt");
         System.out.println("文件编码:" + new FileCharsetDetector().guessFileEncoding(file2));
         
         File file3 = new File("C:\\apps\\uft8_NBOM.txt");
         System.out.println("文件编码:" + new FileCharsetDetector().guessFileEncoding(file3));
         
         File file4 = new File("C:\\apps\\Unicodo.txt");
         System.out.println("文件编码:" + new FileCharsetDetector().guessFileEncoding(file4));
        
         File file5 = new File("C:\\apps\\Hotpoint_755_你去死吧_2_201604251223.hp.zip");
         System.out.println("文件编码:" + new FileCharsetDetector().guessFileEncoding(file5));
         
    }
}