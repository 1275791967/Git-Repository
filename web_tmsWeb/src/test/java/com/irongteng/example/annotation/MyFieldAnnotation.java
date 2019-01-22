package com.irongteng.example.annotation;
import  java.lang.annotation.ElementType;  
import  java.lang.annotation.Retention;  
import  java.lang.annotation.RetentionPolicy;  
import  java.lang.annotation.Target;  
/**  
 * Author: Jiangtao He; Email: ross.jiangtao.he@gmail.com  
 * Date: 2012-1-29  
 * Since: MyJavaExpert v1.0  
 * Description: field annotation  
 */   
@Retention (RetentionPolicy.RUNTIME)   
@Target (ElementType.FIELD)   
public   @interface  MyFieldAnnotation   
{  
    String uri();  
    String desc();  
} 