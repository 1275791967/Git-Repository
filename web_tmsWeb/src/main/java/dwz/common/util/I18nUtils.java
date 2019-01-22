/**
 *-----------------------------------------------------------------------------
 * @ Copyright(c) 2004~2008  Huawei Technologies, Ltd. All Rights Reserved.
 *-----------------------------------------------------------------------------
 * FILE  NAME             : InternationalConfigFile.java
 * DESCRIPTION            :
 * PRINCIPAL AUTHOR       : Huawei Technologies Parlay Project Team
 * SYSTEM NAME            : SAG
 * MODULE NAME            : SAG 
 * LANGUAGE               : Java
 * DATE OF FIRST RELEASE  :
 *-----------------------------------------------------------------------------
 * @ Created on October 04, 2005
 * @ Release 1.0.0.0
 * @ Version 1.0
 * -----------------------------------------------------------------------------------
 * Date           Author          Version     Description
 * -----------------------------------------------------------------------------------
 * 2008-3-12     zhangping        1.0       Initial Create
 * -----------------------------------------------------------------------------------
 */
package dwz.common.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import dwz.framework.spring.SpringContextHolder;

public class I18nUtils {
    
    private static Log log = LogFactory.getLog(I18nUtils.class);
    
    @Autowired(required=false)
    private static HttpServletRequest request;
    
    public static String getMessage(String code, Object[] args) {
        try {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            Locale locale = localeResolver.resolveLocale(request);
    
            return SpringContextHolder.getApplicationContext().getMessage(code, args, locale);
        } catch (Exception e) {
            log.error(e);
            return code;
        }
    }

}
