package com.irongteng.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.irongteng.service.Role;

@Retention (RetentionPolicy.RUNTIME)   
@Target ({ElementType.METHOD,ElementType.TYPE})
public @interface Permission {
    Role[] value() default {Role.ADMIN_ROLE,Role.MANAGER_ROLE,Role.USER_ROLE};
}
