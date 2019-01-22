package com.irongteng.comm.modem;

import java.util.Enumeration;

import gnu.io.CommPortIdentifier;

public class SerialUtils {
    
    public static boolean existSerialPort(String portName) {

        @SuppressWarnings("unchecked")
        Enumeration<CommPortIdentifier> enu= CommPortIdentifier.getPortIdentifiers();
        
        boolean  isFindPort = false;
        while(enu.hasMoreElements()) {
            if(enu.nextElement().getName().equalsIgnoreCase(portName)) {
                isFindPort = true;
            }
        }
        return isFindPort;
    }
}
