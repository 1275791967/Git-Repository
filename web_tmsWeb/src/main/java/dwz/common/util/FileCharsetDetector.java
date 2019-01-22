package dwz.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

public class FileCharsetDetector {
    
    private boolean found = false;
    private String encoding = null;

    /**
     * 传入一个文件(File)对象，检查文件编码
     * 
     * @param file
     *            File对象实例
     * @return 文件编码，若无，则返回null
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String guessFileEncoding(File file) throws FileNotFoundException, IOException {
        return guessFileEncoding(new FileInputStream(file), new nsDetector());
    }
    
    /**
     * <pre>
     * 获取文件的编码
     * @param file
     *            File对象实例
     * @param languageHint
     *            语言提示区域代码 @see #nsPSMDetector ,取值如下：
     *             1 : Japanese
     *             2 : Chinese
     *             3 : Simplified Chinese
     *             4 : Traditional Chinese
     *             5 : Korean
     *             6 : Dont know(default)
     * </pre>
     * 
     * @return 文件编码，eg：UTF-8,GBK,GB2312形式(不确定的时候，返回可能的字符编码序列)；若无，则返回null
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String guessFileEncoding(File file, int languageHint) throws FileNotFoundException, IOException {
        return guessFileEncoding(new FileInputStream(file), new nsDetector(languageHint));
    }
    
    /**
     * 传入一个文件(File)对象，检查文件编码
     * 
     * @param inputStream
     *            InputStream对象实例
     * @return 文件编码，若无，则返回null
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String guessFileEncoding(InputStream inputStream) throws FileNotFoundException, IOException {
        return guessFileEncoding(inputStream, new nsDetector());
    }

    /**
     * <pre>
     * 获取文件的编码
     * @param inputStream
     *            InputStream对象实例
     * @param languageHint
     *            语言提示区域代码 @see #nsPSMDetector ,取值如下：
     *             1 : Japanese
     *             2 : Chinese
     *             3 : Simplified Chinese
     *             4 : Traditional Chinese
     *             5 : Korean
     *             6 : Dont know(default)
     * </pre>
     * 
     * @return 文件编码，eg：UTF-8,GBK,GB2312形式(不确定的时候，返回可能的字符编码序列)；若无，则返回null
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String guessFileEncoding(InputStream inputStream, int languageHint) throws FileNotFoundException, IOException {
        return guessFileEncoding(inputStream, new nsDetector(languageHint));
    }
    
    /**
     * 获取文件的编码
     * 
     * @param file
     * @param det
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    private String guessFileEncoding(InputStream ips, nsDetector det) throws FileNotFoundException, IOException {
        int size = 2048;
        try (BufferedInputStream bis = new BufferedInputStream(ips);
        		BufferedReader br = new BufferedReader(new InputStreamReader(bis, "GB2312"))) {
        	
            if (ips.available() > size)  bis.mark(size); //标记1024个字节
            else bis.mark(ips.available()); 
            
            String line = br.readLine();
            
            bis.reset();
            
            //如果内容为空，则默认返回空
            if (line==null) {
                return encoding = Charset.defaultCharset().name();
            }
            
            //如果通过GB2312能够大开
            if (Charset. forName("GB2312").newEncoder().canEncode(line)) {
                return "GB2312";
            }
            
            det.Init(new nsICharsetDetectionObserver() {
                @Override
                public void Notify(String charset) {
                    encoding = charset;
                    found = true;
                }
            });
            
            byte[] buf = new byte[size];
            int len;
            boolean done = false;
            boolean isAscii = false;
            
            while ((len = bis.read(buf, 0, buf.length)) != -1) {
                // Check if the stream is only ascii.
                isAscii = det.isAscii(buf, len);
                if (isAscii) {
                    break;
                }
                // DoIt if non-ascii and not done yet.
                done = det.DoIt(buf, len, false);
                if (done) {
                    break;
                }
            }
            
            det.DataEnd();

            if (isAscii) {
                encoding = "ASCII";
                found = true;
            }
            
            if (!found) {
                String[] prob = det.getProbableCharsets();
                
                if (prob != null && prob.length>0) {
                    encoding = prob[0];
                } else {
                    encoding = Charset.defaultCharset().name();
                }
            }
        } catch(Exception e) {
            encoding = Charset.defaultCharset().name();
        }
        return encoding;
    }
}