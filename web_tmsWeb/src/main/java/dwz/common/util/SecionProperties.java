/*       
 *     IniReader.java   
 *     用Java读取INI文件(带section的)
 */

package dwz.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;

public class SecionProperties {
    
    private transient String currentSecion;  //保存当前标记
    private final transient StringBuilder currentComment = new StringBuilder(); //当前标记注解
    
    private transient Map<String, String> currentSecionMap;  //当前标记属性值
    private transient Map<String, String> currentCommentMap; //当前标记属性值对应的注解
    
    protected Map<String, Map<String, String>> secions = new LinkedHashMap<>();        //标记属性
    protected Map<String, Map<String, String>> secionComments = new LinkedHashMap<>(); //标记注解
        
    public synchronized void load(Reader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        read(br);
    }
    
    public synchronized void load(InputStream inStream) throws IOException {
        this.load(new InputStreamReader(inStream, "UTF-8"));
    }
    
    protected void read(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            parseLine(line);
        }
    }
    
    protected void parseLine(String line) {
        
        line = line.trim();
        
        if (!line.startsWith("#")) {
            
            if (line.matches("\\[.*\\]")) {
                //添加标签
                currentSecion = line.replaceFirst("\\[(.*)\\]", "$1");
                currentSecionMap = new LinkedHashMap<>();
                secions.put(currentSecion, currentSecionMap);
                //添加注释
                currentCommentMap = new LinkedHashMap<>();
                currentCommentMap.put(currentSecion, currentComment.toString());
                secionComments.put(currentSecion, currentCommentMap);
                //清空临时信息
                currentComment.setLength(0);
                
            } else if (line.matches(".*=.*")) {
                if (currentSecionMap != null) {
                    int i = line.indexOf('=');
                    String key = line.substring(0, i).trim();
                    String value = line.substring(i + 1).trim();
                    if (value.contains("#") && value.indexOf("#") > value.indexOf("=")) {
                        if (!value.contains("\\")) {
                            value = value.substring(0, value.indexOf("#")).trim();
                        } else {
                            int index = 0;
                            while (value.indexOf("#", index) != -1) {
                                System.out.println((value.indexOf("\\",index-2) + 1) +" : " + value.indexOf("#", index));
                                if (value.indexOf("\\",index-1) + 1 == value.indexOf("#", index)) {
                                    index = value.indexOf("#", index + 1);
                                } else {
                                    value = value.substring(0, index).trim();
                                    break;
                                }
                            }
                        }
                    }
                    currentSecionMap.put(key, value);
                    currentCommentMap.put(key, currentComment.toString());
                    //清空临时信息
                    currentComment.setLength(0);
                }
            } else {
                currentComment.append(line);
                currentComment.append(System.getProperty("line.separator"));
            }
        } else {
            currentComment.append(line);
            currentComment.append(System.getProperty("line.separator"));
        }
    }
    
    /**
     * Writes this property list (key and element pairs) in this
     * {@code Properties} table to the output character stream in a
     * format suitable for using the {@link #load(java.io.Reader) load(Reader)}
     * method.
     * <p>
     * @param   writer      an output character stream writer.
     * @param   comments   a description of the property list.
     * @exception  IOException if writing this property list to the specified
     *             output stream throws an <tt>IOException</tt>.
     * @exception  ClassCastException  if this {@code Properties} object
     *             contains any keys or values that are not {@code Strings}.
     * @exception  NullPointerException  if {@code writer} is null.
     * @since 1.6
     */
    public void store(Writer writer, String comments) throws IOException {
        store0((writer instanceof BufferedWriter)?(BufferedWriter)writer : new BufferedWriter(writer), comments, false);
    }

    /**
     * 
     * @param   out      an output stream.
     * @param   comments   a description of the property list.
     * @exception  IOException if writing this property list to the specified
     *             output stream throws an <tt>IOException</tt>.
     * @exception  ClassCastException  if this {@code Properties} object
     *             contains any keys or values that are not {@code Strings}.
     * @exception  NullPointerException  if {@code out} is null.
     * @since 1.2
     */
    public void store(OutputStream out, String comments) throws IOException {
        store0(new BufferedWriter(new OutputStreamWriter(out, "UTF-8")), comments, true);
    }
    
    private void store0(BufferedWriter bw, String comments, boolean escUnicode) throws IOException {
        if (comments != null) {
            writeComments(bw, comments);
        }
        
        synchronized (this) {
            
            Set<String> set = secions.keySet();
            Iterator<String> it = set.iterator();
            
            while(it.hasNext()) {
                String secionName = it.next();
                currentSecionMap = secions.get(secionName);
                if (secionComments.containsKey(secionName)) {
                    currentCommentMap = secionComments.get(secionName);
                } else {
                    currentCommentMap = new LinkedHashMap<>();
                }
                
                if (secionComments.get(secionName) != null 
                        && !"".equals(secionComments.get(secionName).get(secionName))) {
                    bw.write(secionComments.get(secionName).get(secionName));
                }
                
                bw.write("[" + secionName + "]");
                bw.newLine();
                
                //System.out.println("[" + sectionKey + "]");
                
                Set<String> setChild = currentSecionMap.keySet();
                Iterator<String> itChild = setChild.iterator();
                while (itChild.hasNext()) {
                    String key = itChild.next();
                    
                    if (currentCommentMap.containsKey(key)) {
                        String cuKey = currentCommentMap.get(key);
                        
                        if (cuKey != null && !"".equals(cuKey)) {
                            bw.write(currentCommentMap.get(key));
                        }
                    }
                    
                    //System.out.println(key + "=" + currentSecionMap.get(key));
                    bw.write(key + "=" + currentSecionMap.get(key));
                    bw.newLine();
                }
            }
        }
        bw.flush();
    }
    
    private static void writeComments(BufferedWriter bw, String comments)
        throws IOException {
        bw.write("#");
        int len = comments.length();
        int current = 0;
        int last = 0;
        char[] uu = new char[6];
        uu[0] = '\\';
        uu[1] = 'u';
        while (current < len) {
            char c = comments.charAt(current);
            if (c > '\u00ff' || c == '\n' || c == '\r') {
                if (last != current)
                    bw.write(comments.substring(last, current));
                if (c > '\u00ff') {
                    uu[2] = toHex((c >> 12) & 0xf);
                    uu[3] = toHex((c >>  8) & 0xf);
                    uu[4] = toHex((c >>  4) & 0xf);
                    uu[5] = toHex( c        & 0xf);
                    bw.write(new String(uu));
                } else {
                    bw.newLine();
                    if (c == '\r' &&
                        current != len - 1 &&
                        comments.charAt(current + 1) == '\n') {
                        current++;
                    }
                    if (current == len - 1 ||
                        (comments.charAt(current + 1) != '#' &&
                        comments.charAt(current + 1) != '!'))
                        bw.write("#");
                }
                last = current + 1;
            }
            current++;
        }
        if (last != current)
            bw.write(comments.substring(last, current));
        bw.newLine();
    }
    /**
     * Convert a nibble to a hex character
     * @param   nibble  the nibble to convert.
     */
    private static char toHex(int nibble) {
        return hexDigit[(nibble & 0xF)];
    }

    /** A table of hex digits */
    private static final char[] hexDigit = {
        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };
    
    private String secion0(String secionName){
        secionName = secionName.trim();
        
        if (secionName.startsWith("[")&&secionName.endsWith("]")) {
            secionName = secionName.substring(1, secionName.length()-1);
        }
        return secionName;
    }
    
    public Set<String> secionNames() {
        return secions.keySet();
    }
    
    public Map<String, Map<String, String>> secionMaps() {
        return secions;
    }
    
    public Map<String, String> getSecion(String secionName) {
        return secions.get(secion0(secionName));
    }
    
    public String getValue(String secionName, String key) {
        Map<String, String> map = getSecion(secionName);

        if (map == null) return null;

        return map.get(key);
    }
    
    public void putSecion(String secionName, Map<String, String> secionMap) {
        
        secionName = secion0(secionName);
        
        Map<String, String> crtProps = getSecion(secionName);
        
        if (crtProps == null) {  //如果当前标记不存在，则
            secions.put(secionName, secionMap);
        } else {
            crtProps.putAll(secionMap);
            secions.put(secionName, crtProps);
        }
    }
    
    public void putValue(String secionName, String key, String value) {
        
        secionName = secion0(secionName);
        
        Map<String, String> map = getSecion(secionName);
        
        if (map == null) map = new LinkedHashMap<>();
        
        map.put(key, value);
        
        secions.put(secionName, map);
    }
    
    public static void main(String[] args) {
        
        try {
            SecionProperties ini = new SecionProperties();
            ini.load(new FileInputStream("D:/WebCenter/conf/System.ini"));
            
            Map<String, String> ftp = ini.getSecion("FTP");
            ftp.put("ftp.switch", "off");
            ftp.put("ftp.port", "21");
            
            ini.store(new FileOutputStream("D:/WebCenter/conf/System2.ini"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
