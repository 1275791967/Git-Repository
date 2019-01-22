package com.irongteng.example.annotation;
import  java.lang.annotation.*;  
/**  
 * Author: Jiangtao He; Email: ross.jiangtao.he@gmail.com  
 * Date: 2012-1-29  
 * Since: MyJavaExpert v1.0  
 * Description: class annotation  
 */   
@Retention (RetentionPolicy.RUNTIME)   
@Target (ElementType.TYPE)   
public   @interface  MyClassAnnotation   
{  
    String uri();  
    String desc();  
}