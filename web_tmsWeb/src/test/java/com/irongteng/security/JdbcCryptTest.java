package com.irongteng.security;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dwz.common.util.encrypt.DesUtil;

public class JdbcCryptTest {
    
    @Test
    public void testCreateCryptKey() {
        System.out.println(this.getClass().getResource("/").getPath() + "/spring/ipms.key");
        DesUtil.createDesKey(this.getClass().getResource("/").getPath() + "/spring/ipms.key");
    }
    
    @Test
    public void testcryptJdbcProperties() {
        //System.out.println(ClassLoader.getSystemClassLoader().getResourceAsStream("spring/jdbc.properties"));
        //System.out.println(this.getClass().getResource("/spring/jdbc.properties").getPath());
        //System.out.println(this.getClass().getResource("/spring/ipms.key").getPath());
        //System.out.println("classpath:spring/jdbc.properties");
        //System.out.println(ClassLoader.getSystemClassLoader().getResourceAsStream("spring/ipms.key"));
        
        try {
            DesUtil.encrypt("spring/jdbc_plain.properties", 
                    this.getClass().getResource("/").getPath() + "/spring/jdbc.properties", 
                    DesUtil.getKey(ClassLoader.getSystemClassLoader().getResourceAsStream("spring/ipms.key")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testSpringCript() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-context.xml");
        SpringTestBean bean = (SpringTestBean)ctx.getBean("testBean");
        System.out.println(bean.toString());
    }
    
    
}