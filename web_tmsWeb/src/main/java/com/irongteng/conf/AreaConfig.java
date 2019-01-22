/**
 *-----------------------------------------------------------------------------
 * @ Copyright(c) 2004~2008  Huawei Technologies, Ltd. All Rights Reserved.
 *-----------------------------------------------------------------------------
 * FILE  NAME             : AreaConfig.java
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
 * 2008-3-19     zhangping        1.0       Initial Create
 * -----------------------------------------------------------------------------------
 */
package com.irongteng.conf;

public final class AreaConfig extends AbstractSystemConfig {

    private String area;// 区号

    public static AreaConfig config = null;

    private AreaConfig() {
        try {
            this.loadSecionProperties("AreaCode");
            
            setArea(this.getProperty("area", "+86"));
        } catch(Exception e) {
            logger.error(e.getMessage());
        }
    }
    
    /**
     * 单例类，获取该类的唯一对象
     * 
     * @return
     */
    public static AreaConfig getInstance() {
        if (config == null) {
            config = new AreaConfig();
        }
        return config;
    }
    
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        setProperty("area", area);
        this.area = area;
    }
    
    public static void main(String[] args) {
        AreaConfig fmb = AreaConfig.getInstance();
        System.out.println(fmb.getArea());
    }
}