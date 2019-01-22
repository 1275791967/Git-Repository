package com.irongteng.protocol.common;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.irongteng.protocol.Protocol;
import com.irongteng.protocol.ProtocolConst;
/**
 * @author lvlei
 * 
 */
public class CommonProtocol implements Protocol<CommonParamValueObject> {
    
private static volatile short commPackageID = 1; // 通信包标识号
    
    private String errorMsg; // 记录组装或解析数据包的错误信息
    
    /**
     * 实现Protocol接口的方法     * 
     * @param paramObject 设备与参数对象
     * @return 返回组包的字节流
     */
    @Override
    public byte[] encode(CommonParamValueObject paramObject) {
        try {
            Map<Short,CommonMonitorObject> dataMap = paramObject.getDataMap();
            // 获取所有监控对象的长度
            int length = getDataLength(dataMap);
            int dataAllSize= length + 15;
            ByteBuffer buffer = ByteBuffer.allocate(dataAllSize).order(ByteOrder.LITTLE_ENDIAN);
            buffer.putInt(dataAllSize);  //设备编号
        	
        	//版本信息
            Byte versionID = paramObject.getVersionID();
            
            if (versionID == null) versionID = ProtocolConst.VERSION_V2;
            if (versionID != ProtocolConst.VERSION_V2 && versionID != ProtocolConst.VERSION_V3) { 
            	errorMsg = "版本号不匹配(版本信息)";
                return null;
            }
            buffer.put(versionID);  //版本号
        	
            Integer deviceNumber = paramObject.getDeviceNumber();
            if (deviceNumber == null) {
            	deviceNumber = 0;
                paramObject.setDeviceNumber(deviceNumber);
            }
            buffer.putInt(deviceNumber); //设备编号
            //包序号
            Short commPkgID = paramObject.getCommPackageID();
            if (commPkgID == null) {
                //获取自增的包序号
                commPkgID = raiseCommPackageID();
                paramObject.setCommPackageID(commPkgID);
            }
            buffer.putShort(commPkgID);
            
            buffer.mark();              //验证码标记位
            buffer.putShort((short) 0); //验证码先默认为0
            
            //命令编号
            Byte commandID = paramObject.getCommandID();
            if (commandID == null) { //缺少参数(命令编号)
                errorMsg = "缺少参数(命令编号)";
                return null;
            }
            buffer.put(commandID);
            //应答编号
            Byte answerID = paramObject.getAnswerID();
            if (answerID == null) {
            	errorMsg = "缺少参数(应答标识)";
            	return null;
            }
            if (answerID != ProtocolConst.ANSWER_FLAG) {
            	errorMsg = "应答标识必须为0(应答标识)";
            	return null;
            }
            buffer.put(answerID);
            
            //获取参数数据
            if (dataMap != null && dataMap.size() > 0) {
            	// 循环获取监控对象的字节流,根据参数信息生成MCP层数据包
            	//非上报响应
                Iterator<Short> it = dataMap.keySet().iterator();
                CommonMonitorObject value;
                byte[] tempBytes;
                
                while (it.hasNext()) {
                    value = dataMap.get(it.next());
                    if (value != null) {
                        tempBytes = value.toBytes();
                        buffer.put(tempBytes);
                    }
                }
            }
            //生成验证码
            byte[] result = buffer.array();
            if (versionID == ProtocolConst.VERSION_V3) {
            	short checkCode = 0;
            	for (byte b: result) {
            		checkCode += b&0xff;
            	}
            	buffer.reset();
            	buffer.putShort(checkCode);
            }
            return buffer.array();
        } catch (Exception e) {
            //组装据包发生异常
            errorMsg = "组装据包发生异常";
            return null;
        }
    }

    /**
     * 实现SouthProtocolInterface接口的方法
     * 
     * @param pkg
     *            需要解析的字节流
     * @param paramObject
     * 
     * @return 返回解析结果，true表示成功，false表示失败
     * 
     */
    @Override
    public boolean decode(byte[] pkg, CommonParamValueObject paramObject) {
        
        try {
            if (pkg.length < 69) {
                errorMsg = "数据长度太短，不是合法的数据！";
                return false;
            }
            short codeResult = 0;
            for (int i=0; i < pkg.length; i++) {
                if (i != 11 && i != 12) codeResult += pkg[i]&0xFF; //非验证码段累加
            }

            ByteBuffer buffer = ByteBuffer.wrap(pkg).order(ByteOrder.LITTLE_ENDIAN);

            int dataSize = buffer.getInt();
            if (dataSize != pkg.length) {
                errorMsg = "参数标记长度与实际数据长度不匹配";
                return false;
            }
            paramObject.setDataSize(dataSize);
            //版本号
            byte versionID = buffer.get();
            if (versionID != ProtocolConst.VERSION_V2 && versionID != ProtocolConst.VERSION_V3) { 
            	errorMsg = "版本号不匹配(版本信息)";
                return false;
            }
            paramObject.setVersionID(versionID);
            //设备编号
            int deviceNumber = buffer.getInt();
            paramObject.setDeviceNumber(deviceNumber);
            //包编号标识符
            short commPkgID = buffer.getShort();
            paramObject.setCommPackageID(commPkgID);
            //校验码
            short checkCode = buffer.getShort();
            
            if (versionID == ProtocolConst.VERSION_V3) {
                if (codeResult != checkCode) {
                    errorMsg = "校验码错误(校验和)";
                    return false;
                }
            }
            //命令编号
            byte commandID = buffer.get();
            paramObject.setCommandID(commandID);
            //应答标志
            byte answerID = buffer.get();
            paramObject.setAnswerID(answerID);
            if (answerID != ProtocolConst.ANSWER_FLAG) { 
            	errorMsg = "应答标识必须为0(应答标识)";
                return false;
            }
            //获取参数数据
            Map<Short,CommonMonitorObject> dataMap = new HashMap<>();
            
            CommonMonitorObject mo;
            
            while(buffer.hasRemaining()) {
                int remaining = buffer.remaining();
                //目前翻译调度系统,保留字段50个字节。所以数据长度最低54个字节
                if (remaining < 54) {
                    errorMsg = "参数数据长度不合法(参数数据)";
                    return false;
                }
                short vSize = buffer.getShort();
                
                if (remaining >= vSize) {
                    short moID = buffer.getShort();
                    short molen = (short) ((vSize & 0xFFFF) - 4);
                    
                    byte[] moValue = new byte[molen];
                    buffer.get(moValue);
                    
                    mo = new CommonMonitorObject(moID, molen, moValue);
                    
                    //查询返回或者定时上报
                    dataMap.put(moID, mo);
                } 
            }
            paramObject.setDataMap(dataMap);
            // 返回解析结果
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            // 解析VP层数据包发生异常";
            errorMsg = "解析数据包发生异常";
            return false;
        }
    }

    /**
     * 实现SouthProtocolInterface接口的方法
     */
    @Override
    public String getParseMessage() {
        // 返回errorMsg的值
        return errorMsg;
    }
    
    @Override
    public Map<Short, byte[]> encodeMap(CommonParamValueObject paramObject) {
        
        Map<Short, byte[]> encodeResult = new HashMap<>();
        //分包
        List<CommonParamValueObject> list = splitDataMap(paramObject);
        
        if (list == null || list.size() <= 0) {
            byte[] bytes = encode(paramObject);
            if (bytes != null) {
                encodeResult.put(paramObject.getCommPackageID(), bytes);
            }
        } else {
            list.stream().forEach((paramValObj) -> {
                byte[] bytes = encode(paramValObj);
                if (bytes != null) {
                    encodeResult.put(paramValObj.getCommPackageID(), bytes);
                }
            });
        }
        return encodeResult;
    }
    
    /**
     * 生成通信包标识
     * 
     * @return
     */
    public static short raiseCommPackageID() {
        if (commPackageID >= 0x7FFF) {
            commPackageID = 0;
        }
        return commPackageID++;
    }
    
    /**
     * 计算data中数据的长度
     * 
     * @param data
     *            存放的一个或多个监控对象
     * @return 返回data中数据的长度
     */
    private int getDataLength(Map<Short, CommonMonitorObject> data) {
        if (data == null) {
            return 0;
        }
        // 计算data长度
        int length = 0;
        Short key;
        CommonMonitorObject value;
        try {
            Iterator<Short> it = data.keySet().iterator();
            while (it.hasNext()) {
                key = it.next();
                value = data.get(key);
                if (value != null && (value instanceof CommonMonitorObject)) {
                    int size = value.size();
                    if (size < 4) {
                        return 0;
                    }
                    length += size;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回长度
        return length;
    }
    
    /**
     * 数据拆包处理
     * @param baseMap
     * @param dataMap
     * @return
     */
    private List<CommonParamValueObject> splitDataMap(CommonParamValueObject paramObject) {
        
        List<CommonParamValueObject> list = new ArrayList<>();
        
        try {
            //版本信息
            Byte versionID = paramObject.getVersionID();
            if (versionID == null) { //缺少参数(版本信息)
                errorMsg = "缺少参数(版本信息)";
                return null;
            }
            if (versionID != ProtocolConst.VERSION_V2 && versionID != ProtocolConst.VERSION_V3) { 
            	errorMsg = "版本号不匹配(版本信息)";
                return null;
            }
            //设备编号
            Integer deviceNumber = paramObject.getDeviceNumber();
            if (deviceNumber == null) { // 缺少参数(设备编号)
            	deviceNumber = 0;
            }
            //命令编号
            Byte commandID = paramObject.getCommandID();
            if (commandID == null) { //缺少参数(命令编号)
                errorMsg = "缺少参数(命令编号)";
                return null;
            }
            
            //应答编号
            Byte answerID = paramObject.getAnswerID();
            if (answerID == null) { //缺少参数(应答编号)
                errorMsg = "缺少参数(应答编号)";
                return null;
            }
            
            Map<Short, CommonMonitorObject> dataMap = paramObject.getDataMap();
            
            Map<Short,CommonMonitorObject> newDataMap = new HashMap<>();
            List<Map<Short,CommonMonitorObject>> dataMapList = new ArrayList<>();
            
            int length = 0; Short moID; CommonMonitorObject mo;
            
            Iterator<Short> it = dataMap.keySet().iterator();
            while (it.hasNext()) {
                
                moID = it.next();
                mo = dataMap.get(moID);
                int size = mo.size();
                
                if (size < 4)  continue;
                
                length += size;
                
                if (length >= 1000-15) {
                	
                	dataMapList.add(new HashMap<>(newDataMap));
                	
                    newDataMap.clear();
                    length = size;
                }
                
                newDataMap.put(moID, mo);
            }
            //最后看看Map列表中是否有数据，有则添加进去。
            if (newDataMap.size() > 0) dataMapList.add(new HashMap<>(newDataMap));
            
            for (Map<Short,CommonMonitorObject> map : dataMapList){
            	CommonParamValueObject paramObj = new CommonParamValueObject();
            	paramObj.setVersionID(versionID);
            	paramObj.setDeviceNumber(deviceNumber);
            	paramObj.setCommandID(commandID);
            	paramObj.setAnswerID(answerID);
                
            	paramObj.setDataMap(map);
                
                list.add(paramObj);
            }
            
        } catch (Exception e) {
            errorMsg = e.getMessage();
        }
        return list;
    }
}