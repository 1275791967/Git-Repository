package com.irongteng.conf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public final class ModemConfig extends AbstractSystemConfig {
    
    private List<ModemInfo> modems = new ArrayList<>();
    
    public ModemConfig(){
        
        try {
            
            this.loadSecionProperties("Modem");
            
            Properties props = this.getProperties();
            
            Set<Entry<Object, Object>> set = props.entrySet();
            Iterator<Entry<Object, Object>> it = set.iterator();
            
            while(it.hasNext()) {
                Entry<Object, Object> entry = it.next();
                String portName = (String) entry.getKey();
                String info = (String) entry.getValue();
                ModemInfo modem = new ModemInfo(portName, info);
                
                modems.add(modem);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
    
    public List<ModemInfo> getModems() {
        return modems;
    }

    public void setModems(List<ModemInfo> modems) {
        modems.forEach((modem) -> {
            setProperty(modem.getPortName(), modem.getPortInfo());
        });
        this.modems = modems;
    }
    
    public void addModem(ModemInfo modemInfo) {
        setProperty(modemInfo.getPortName(), modemInfo.getPortInfo());
        this.modems.add(modemInfo);
    }
    
    public static void main(String[] args) {
        
        ModemConfig fmb = new ModemConfig();
        List<ModemInfo> modems = fmb.getModems();
        
        modems.forEach((info) -> {
            System.out.println(info.getPortName() + "," + info.getModemType() + "," + info.getBaudRate());
        });
    }
}
