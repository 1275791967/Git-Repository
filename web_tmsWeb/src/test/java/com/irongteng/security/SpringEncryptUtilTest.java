package com.irongteng.security;

import org.junit.Test;

import dwz.common.util.encrypt.DesEncryptUtil;
import dwz.common.util.encrypt.SpringEncryptUtil;

public class SpringEncryptUtilTest {
    
    @Test
    public void testDES() {
        long time = System.currentTimeMillis();

        System.out.println(SpringEncryptUtil.encrypt("admin","123456"));
        System.out.println(SpringEncryptUtil.encrypt("test"));
        System.out.println(SpringEncryptUtil.encrypt("test"));
        
        System.out.println(SpringEncryptUtil.match("admin", "123456", "843B1FC1F216458A7B0D9E41BF7445492442D2A1B0128CFD24236A57347192137938563AC6E4F120"));
        System.out.println(SpringEncryptUtil.match("test", "F20C8BA39A49EF267D632A09FE5DC9657829F45688430C619A30A3DE2834F593CCD15746FE67E9D9"));
        System.out.println(System.currentTimeMillis() - time);
        System.out.println(DesEncryptUtil.encrypt("843B1FC1F216458A7B0D9E41BF7445492442D2A1B0128CFD24236A57347192137938563AC6E4F120"));
    }
}
