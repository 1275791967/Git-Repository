package com.irongteng.example.annotation;

import java.lang.reflect.Method;

import com.irongteng.annotation.Permission;
import com.irongteng.service.Role;

public class AnnotationMain {
    
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE,Role.USER_ROLE})
    public void getString() {
        
    }
    public static void main(String[] args) {
        Method[] oMethods = AnnotationMain.class.getDeclaredMethods();
        for (Method method: oMethods) {
            if (method.isAnnotationPresent(Permission.class)) {
                Permission myMethodAnno = method.getAnnotation(Permission.class );  
                System.out.println("method name:" + method.getName() +" Method's value: "  + myMethodAnno.value()); 
                Role[] roles = myMethodAnno.value();
                for(Role role: roles) {
                    String roleString = role.name();
                    System.out.println(roleString);
                }
            }
        }
    }

}
