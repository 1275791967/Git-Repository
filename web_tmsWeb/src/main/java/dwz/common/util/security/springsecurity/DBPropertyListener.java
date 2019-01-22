package dwz.common.util.security.springsecurity;

import java.util.Properties;

import dwz.framework.spring.SpringContextHolder;

public class DBPropertyListener implements PropertyListener{
    
    @Override
    public void mergeProperties(Properties props) {
        System.out.println(props.getProperty("jdbc.username"));
        System.out.println(props.getProperty("jdbc.password"));
    }
    
    public static void main(String[]args) {
        DecryptPropertyPlaceholderConfigurer decrypt = SpringContextHolder.getBean("propertyConfigurer");
        
        decrypt.SetPropertiesListener(new DBPropertyListener());
    }
}
