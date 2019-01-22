package dwz.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

public abstract class AbstractConfig {
    
    private SecionProperties secionProperties;
    
    private Map<String, String> secion = new LinkedHashMap<>();
    
    protected Logger logger = Logger.getLogger(this.getClass());
    
    private final Properties properties = new Properties();
    
    private File secionPropertiesFile;
            
    private String secionName;
    
    protected final void loadSecionProperties(String sectionName) {
        
        this.secionName = sectionName;
        Map<String, String> map = secionProperties.getSecion(sectionName);
        //如果标记不存在，则创建新的标记
        this.secion = map != null ? map : new LinkedHashMap<>();
        
        loadSecion2Properties();
    }

    protected Properties getProperties() {
        return properties;
    }
    
    protected String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    protected String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    protected void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
    
    /**
     * 加载
     * @param secionPropertiesFile
     */
    protected final void load (File secionPropertiesFile) {
        this.secionPropertiesFile = secionPropertiesFile;
        
        secion.clear();
        properties.clear();
        
        try {
            secionProperties = new SecionProperties();
            secionProperties.load(new FileInputStream(secionPropertiesFile));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        } catch (IOException | IllegalStateException e) {
            logger.error(e.getMessage());
        }
    }
    /**
     * 加载
     * @param secionPropFilePath
     */
    protected final void load (String secionPropFilePath) {
        this.load(new File(secionPropFilePath));
    }
    
    /**
     * 加载secion到属性文件
     */
    private void loadSecion2Properties() {
        
        Set<String> set = secion.keySet();
        Iterator<String> it = set.iterator();
        
        while (it.hasNext()) {
            String key = it.next();
            String value = secion.get(key);
            
            properties.setProperty(key, value);
        }
    }
    
    /**
     * 存储属性文件到secion
     */
    private void storeProperties2Secion() {
        
        Set<String> set = properties.stringPropertyNames();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = properties.getProperty(key);
            
            secion.put(key, value);
        }
    }
    
    /**
     * 保存
     * 
     */
    public void save() {
        
        storeProperties2Secion();
        
        if (secionName != null) secionProperties.putSecion(secionName, secion);
        
        try {
            secionProperties.store(new FileOutputStream(secionPropertiesFile), null);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    
}
