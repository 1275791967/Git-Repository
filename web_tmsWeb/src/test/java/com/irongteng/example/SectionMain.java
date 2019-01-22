package com.irongteng.example;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.junit.Test;

public class SectionMain {

    @Test
    public void testSectionProperties() {
        
        Section s1 = new Section();
        s1.setKey("lvlei");
        s1.setValue("tangtang1");
        
        Section s2 = new Section();
        s2.setKey("lvlei");
        s2.setValue("tangtang2");
        Set<Section> set = new HashSet<Section>();
        set.add(s1);
        set.add(s2);
        
        System.out.println(s1.equals(s2));
        System.out.println(set.size());
        System.out.println();
        Iterator<Section> it = set.iterator();
        while(it.hasNext()) {
            Section s = it.next();
            System.out.println(s.getKey() + ":" + s.getValue());
        }
    }
    
    @Test
    public void testSectionProperties_2() {
        Properties p = new Properties();
        p.put("name", "zhangsan");
        p.put("name", "lisi");
        System.out.println(p.getProperty("name"));
    }
}
