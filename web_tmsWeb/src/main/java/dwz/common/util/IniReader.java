/*       
 *     IniReader.java   
 *     用Java读取INI文件(带section的)   
 *     示例：       
 *           tmp.IniReader   reader   =   new   tmp.IniReader("E://james//win.ini");   
 *           out.println(reader.getValue("TestSect3",   "kkk   6"));   
 */

package dwz.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class IniReader {
    
    protected HashMap<String, Properties> sections = new HashMap<String, Properties>();
    private transient String currentSecion;
    private transient Properties current;

    public IniReader(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        read(reader);
        reader.close();
    }
    
    public IniReader(String filename) throws IOException {
        this(new File(filename));
    }
    
    protected void read(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            parseLine(line);
        }
    }
    
    protected void parseLine(String line) {
        line = line.trim();
        
        if (line.matches("\\[.*\\]")) {
            currentSecion = line.replaceFirst("\\[(.*)\\]", "$1");
            current = new Properties();
            sections.put(currentSecion, current);
        } else if (line.matches(".*=.*")) {
            if (current != null) {
                int i = line.indexOf('=');
                String name = line.substring(0, i);
                String value = line.substring(i + 1);
                current.setProperty(name, value);
            }
        }
    }
    
    public Properties getSection(String section) {
        section = section.trim();
        
        if (section.startsWith("[")&&section.endsWith("]")) {
            section = section.substring(1, section.length()-1);
        }
        return sections.get(section);
    }
    
    public String getValue(String section, String name) {
        Properties p = getSection(section);

        if (p == null) {
            return null;
        }

        String value = p.getProperty(name);
        return value;
    }
    
    public static void main(String[] args) {
        
        try {
            IniReader ini = new IniReader("D:/WebCenterV2/WebCenter/conf/System.ini");
            String ftpSwitch = ini.getValue("[FTP]", "ftp.switch");
            Properties ftp = ini.getSection("FTP");
            System.out.println(ftpSwitch + ":" + ftp.getProperty("ftp.switch"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
