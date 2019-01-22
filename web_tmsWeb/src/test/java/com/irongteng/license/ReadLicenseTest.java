package com.irongteng.license;

import org.junit.Test;

public class ReadLicenseTest {
    
    @Test
    public void testVerify() {
        
        try {
            System.out.println(ReadLicense.verify());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
