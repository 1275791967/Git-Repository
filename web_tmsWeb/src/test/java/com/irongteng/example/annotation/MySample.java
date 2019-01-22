package com.irongteng.example.annotation;
import  java.lang.reflect.*;  
/**  
 * Author: Jiangtao He; Email: ross.jiangtao.he@gmail.com  
 * Date: 2012-1-29  
 * Since: MyJavaExpert v1.0  
 * Description: This class is used to show how to use the annotation of each level  
 */   
@MyClassAnnotation(uri = "com.ross.MySample", desc = "The class name")
public class MySample {
    @MyFieldAnnotation(uri = "com.ross.MySample#id", desc = "The class field")
    public String id;

    /**
     * Description: default constructor
     */
    @MyConstructorAnnotation(uri = "com.ross.MySample#MySample", desc = "The default constuctor")
    public MySample() {
    }

    /**
     * Description: normal method
     */
    @MyMethodAnnotation(uri = "com.ross.MySample#setId", desc = "The class method")
    public void setId(String id) {
        this.id = id;
    } 
    @MyMethodAnnotation(uri = "com.ross.MySample#gId", desc = "The class method")
    public String getId() {
        return id;
    }
    /**  
     * Description: MyAnnotation test  
     * @throws NoSuchMethodException   
     * @throws SecurityException   
     * @throws NoSuchFieldException   
     */   
    public static void main(String[] args) throws SecurityException,
            NoSuchMethodException, NoSuchFieldException {
        MySample oMySample = new  MySample();  
        // get class annotation   
        MyClassAnnotation oMyAnnotation = MySample.class.getAnnotation(MyClassAnnotation.class );  
        System.out.println("Class's uri: "  + oMyAnnotation.uri() +  "; desc: "  + oMyAnnotation.desc());  
  
        // get constructor annotation   
        Constructor<? extends MySample> oConstructor = oMySample.getClass().getConstructor();  
        MyConstructorAnnotation oMyConstructorAnnotation = oConstructor.getAnnotation(MyConstructorAnnotation.class );  
        System.out.println("Constructor's uri: " + oMyConstructorAnnotation.uri() + "; desc: " + oMyConstructorAnnotation.desc());  
        
        // get method annotation   
        Method oMethod = oMySample.getClass().getDeclaredMethod("setId" ,String.class );  
        MyMethodAnnotation oMyMethodAnnotation = oMethod.getAnnotation(MyMethodAnnotation.class );  
        System.out.println("Method's uri: "  + oMyMethodAnnotation.uri() + "; desc: "  + oMyMethodAnnotation.desc());  
        Method[] oMethods = oMySample.getClass().getDeclaredMethods();
        for (Method method: oMethods) {
            if (method.isAnnotationPresent(MyMethodAnnotation.class)) {
                MyMethodAnnotation myMethodAnno = method.getAnnotation(MyMethodAnnotation.class );  
                System.out.println("method name:" + method.getName() +" Method's uri: "  + myMethodAnno.uri() + "; desc: "  + myMethodAnno.desc());  
            }
        }
        //MyMethodAnnotation oMyMethodAnnotation = oMethod.getAnnotation(MyMethodAnnotation.class );  
        
        
        // get field annotation   
        Field oField = oMySample.getClass().getDeclaredField("id" );  
        MyFieldAnnotation oMyFieldAnnotation = oField.getAnnotation(MyFieldAnnotation.class );  
        System.out.println("Field's uri: "  + oMyFieldAnnotation.uri() + "; desc: "  + oMyFieldAnnotation.desc());  
  
    }  
  
}  