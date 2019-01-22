
package dwz.common.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;
/**
 * 混合两个对象为一个Map对象
 * MapAndObject.get()方法将在两个对象取值,Map如果取值为null,则再在Bean中取值
 * @author peng.shi
 */
public class MapAndObjectTest{

    @Test
    public void getBean() {
        
    }
    
    @Test
    public void get() {
        
    }
    
    @Test
    public void testClear() {
        
    }

    @Test
    public void containsKey() {

    }

    public void containsValue() {
    }

    @Test
    public void testEntrySet() {
        
    }

    @Test
    public void isEmpty() {
        
    }

    @Test
    public void keySet() {
        
    }

    @Test
    public void put() {
        
    }
    
    @Test
    public void testPutAll() {
        
    }
    
    @Test
    public void testRemove() {
        
    }

    @Test
    public void testSize() {
        
    }

    @Test
    public void testValues() {
        
    }
    
    @Test
    public void testMain() {
        Map<Object,Object> map = new HashMap<Object, Object>();
        map.put(1, 2);
        map.put("3", "4");
        
        Map<Object,Object> map2 = new HashMap<Object, Object>();
        map.put(1, 2);
        map.put("3", "4");
        
        
        MapAndObject mo = new MapAndObject(map, map2);
        Object c = mo.get(1);
        System.out.println(c);
        Collection<Object> cc = mo.values();
        Iterator<Object> it =cc.iterator();
            
        while(it.hasNext()) {
            System.out.println("" + it.next());;
        }
    }
}

