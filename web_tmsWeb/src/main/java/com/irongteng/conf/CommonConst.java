package com.irongteng.conf;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommonConst {
    //设备类型
    public static Map<Integer, String> getDeviceType() {
        Map<Integer,String> map = new LinkedHashMap<>();
        map.put(1, "翻译前端");
        map.put(2, "翻译服务器");
        map.put(3, "手机客户端");
        return Collections.unmodifiableMap(map);
    }
    
    
    //翻译通道类型
    public static Map<Integer, String> getChannelType() {
        Map<Integer,String> map = new LinkedHashMap<>();
        map.put(1, "请求翻译");
        map.put(2, "取消翻译");
        map.put(3, "翻译成功");
        map.put(4, "翻译失败");
        map.put(5, "系统翻译");
        map.put(6, "第三方翻译");
        return Collections.unmodifiableMap(map);
    }

}
