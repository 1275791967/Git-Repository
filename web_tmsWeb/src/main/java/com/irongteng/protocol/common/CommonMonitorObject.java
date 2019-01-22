/**
 * 参数对象对方的封装与解析
 */
package com.irongteng.protocol.common;

import java.io.UnsupportedEncodingException;

import com.irongteng.protocol.MonitorObject;

import dwz.common.util.StringUtils;

/**
 * MCPA协议承载的监控对象
 * @author winhap123456
 *
 */
public final class CommonMonitorObject implements MonitorObject<Short, Short>{
    
    private short objectID;  //监控对象标识
    
    private short length;    //监控对象值的长度
    
    private byte[] value;    //监控数据的值
    
    private String dataType; //监控参数类型
    
    /**
     * 构造函数
     * @param objectID 监控对象标识
     * @param length 监控对象长度
     */
    public CommonMonitorObject(short objectID, short length) {
        
        setObjectID(objectID);

        setLength(length);
    }
    /**
     * 构造函数
     * @param objectID 监控对象标识
     * @param length 监控对象长度
     * @param value 监控数据的值
     */
    public CommonMonitorObject(short objectID, short length, byte[] value) {

        setObjectID(objectID);

        setLength(length);

        setValue(value);
    }
    
    /**
     * 构造函数
     * @param objectID 监控对象标识
     * @param length 监控对象长度
     * @param value 监控数据的值
     * @param dataType 数据类型
     */
    public CommonMonitorObject(short objectID, short length, String value, String dataType) {

        setObjectID(objectID);

        setLength(length);

        setValue(value, dataType);
        
        setDataType(dataType);
    }
    
    /**
     * 获取监控对象的长度
     * @return 返回监控对象的长度
     */
    @Override
    public int size() {
        return (length & 0xFFFF) + 4;
    }
    
    @Override
    public Short getObjectID() {
        return objectID;
    }
    
    @Override
    public void setObjectID(Short objectID) {
        this.objectID = objectID;
    }

    @Override
    public Short getLength() {
        return length;
    }
    
    @Override
    public void setLength(Short length) {
        this.length = length;
        value = new byte[length & 0xFFFF];
        fillValue((byte) 0);
    }
    
    /**
     * 获取参数值得字节数组
     * 
     */
    @Override
    public byte[] getValue() {
        return value;
    }
    
    /**
     * 根据数据类型获取字符串类型参数值
     * @param dataType 数据类型
     */
    @Override
    public String getValue(String dataType) {
        
        if (dataType == null) return null;
        
        dataType = dataType.trim().toLowerCase();
        
        switch (dataType) {
            case "sint1":
                return String.valueOf(value[0]);
                //return (new Integer(value[0])).toString();
            case "uint1":
                return (new Integer(value[0] & 0xFF)).toString();
            case "sint2":
            {
                short s = (short) ((value[1] << 8) + (value[0] & 0xFF));
                return (new Integer(s)).toString();
            }
            case "uint2":
            {
                int s = ((value[1] & 0xFF) << 8) + (value[0] & 0xFF);
                return (new Integer(s)).toString();
            }
            case "uint3":
            {
                int s = ((value[2] & 0xFF) << 16) + ((value[1] & 0xFF) << 8)
                        + (value[0] & 0xFF);
                return (new Integer(s)).toString();
            }
            case "sint4":
            {
                int l = ((value[3] & 0xFF) << 24) + ((value[2] & 0xFF) << 16)
                        + ((value[1] & 0xFF) << 8) + (value[0] & 0xFF);
                return (new Long(l)).toString();
            }
            case "uint4":
            {
                long l = ((value[3] & 0xFFL) << 24) + ((value[2] & 0xFF) << 16)
                        + ((value[1] & 0xFF) << 8) + (value[0] & 0xFF);
                return (new Long(l)).toString();
            }
            case "string":
            {
                String str = "";
                try {
                    switch (objectID) {
                        case 0x0044:
                            //设备名称
                            str = new String(value, "GBK").trim();
                            break;
                        case 0x005E:
                            //如果为短信版内容，则使用UTF-8编码，否则使用GBK编码
                            str = new String(value, "UTF-8").trim();
                            break;
                        default:
                            str = new String(value, "UTF-8").trim();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                if(str.contains("\00")) {
                    str = str.substring(0, str.indexOf("\00"));
                }
                return str;
            }
            case "str":
            {
                String str = new String(value);
                return str.trim();
            }
            case "bit":
                if (value[0] == 0) {
                    return "0";
                } else {
                    return "1";
                }
            case "number":
            {
                String str = "";
                for (int i = 0; i < this.length; i++) {
                    str = str + (new Integer(value[i] & 0xFF)).toString();
                }
                return str;
            }
            case "bcd":
                return BCDToStr(getValue());
            case "ip":
            {
                byte[] bs = this.getValue();
                String str = "";
                str = str + Integer.toString(bs[0] & 0xFF) + "."
                        + Integer.toString(bs[1] & 0xFF) + "."
                        + Integer.toString(bs[2] & 0xFF) + "."
                        + Integer.toString(bs[3] & 0xFF);
                return str;
            }
            case "uint12":
            case "uint18":
            case "sint49":
            case "uint32":
            {
                byte[] bs = this.getValue();
                String str = StringUtils.bytes2HexString(bs, null).toUpperCase();
                return str;
            }
            default:
                return null;
        }
    }
    
    /** 设置阐述值
     * 未解决设置的字符串为汉字问题重新此方法
     * lvlei 2013-12-5
     * @param value 字符串类型的参数值
     * @param dataType  值类型
     */
    @Override
    public void setValue(String value, String dataType) {
        
        this.dataType = dataType;
        
        if (value == null) {
            return;
        }
        
        dataType = dataType.trim().toLowerCase();
        
        switch (dataType) {
            case "sint1":
                setValue((byte) Integer.parseInt(value));
                break;
            case "uint1":
                setValue((byte) Integer.parseInt(value));
                break;
            case "sint2":
                setValue((short) Integer.parseInt(value));
                break;
            case "uint2":
                setValue((short) Integer.parseInt(value));
                break;
            case "uint3":
                setValue(Integer.parseInt(value));
                break;
            case "sint4":
                setValue((int) Long.parseLong(value));
                break;
            case "uint4":
                setValue((int) Long.parseLong(value));
                break;
            case "string":
                setValue(value);
                break;
            case "bit":
                setValue((byte) Integer.parseInt(value));
                break;
            case "number":
                setValue(value);
                break;
            case "bcd":
                {
                    byte[] bs = strToBCD(value);
                    setValue(bs);
                    break;
                }
            case "ip":
                {
                    String str = "";
                    for (int i = 0; i < value.length(); i++) {
                        if (value.charAt(i) == '.') {
                            str = str + "#";
                        } else {
                            str = str + value.charAt(i);
                        }
                    }       String ss[] = str.split("#");
                    if (ss.length < 4)
                        return;
                    byte[] bs = new byte[4];
                    for (int i = 0; i < 4; i++) {
                        try {
                            bs[i] = (byte) Integer.parseInt(ss[i]);
                        } catch (Exception e) {
                            return;
                        }
                    }
                    setValue(bs);
                    break;
                }
            default:
                break;
        }
    }
    
    /**
     * 获取数据类型
     */
    @Override
    public String getDataType() {
        return dataType;
    }
    
    /**
     * 设置数据类型
     * @param dataType
     */
    @Override
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * 获取字符串的字符类型的参数值
     * @return
     */
    @Override
    public String getStringValue() {
        return getValue(dataType);
    }
    
    /**
     * 将监控对象转换成字节流
     * @return
     */
    @Override
    public byte[] toBytes() {
        byte[] rs = new byte[size()];
        short bSize = (short) (length + 4);
        
        rs[0] = (byte) (bSize & 0xFF);
        rs[1] = (byte) ((bSize >> 8)  & 0xFF);
        
        rs[2] = (byte) (objectID & 0xFF);
        rs[3] = (byte) ((objectID >> 8) & 0xFF);
        
        for (int i = 0; i < value.length; i++) {
            rs[i + 4] = value[i];
        }
        return rs;
    }
    
    private void setValue(byte[] value) {
        if (value == null) {
            return;
        }
        int k = (value.length > this.value.length) ? this.value.length
                : value.length;
        for (int i = 0; i < k; i++) {
            this.value[i] = value[i];
        }
    }
    
    private void setValue(String value) {
        if (value == null) {
            return;
        }
        fillValue((byte) 0);
        try {
            byte[] bytes;
            switch (objectID) {
                case 0x0044:
                    //设备名称
                    bytes = value.getBytes("GBK");
                    break;
                case 0x005E:
                    //如果为短信版内容，则使用UTF-8编码，否则使用GBK编码
                    bytes = value.getBytes("UTF-8");
                    break;
                default:
                    bytes = value.getBytes("UTF-8");
                    break;
            }
            int k = bytes.length> this.value.length ? this.value.length : bytes.length;
            System.arraycopy(bytes, 0, this.value, 0, k);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
    }
    
    private void setValue(byte value) {
        fillValue((byte) 0);
        if (this.value.length > 0) {
            this.value[0] = value;
        }
    }
    
    private void setValue(short value) {
        int k = (this.value.length > 2) ? 2 : this.value.length;
        fillValue((byte) 0);
        if (k == 2) {
            this.value[0] = (byte) (value & 0x00FF);
            this.value[1] = (byte) ((value >> 8) & 0x00FF);
        } else if (k == 1) {
            this.value[0] = (byte) (value & 0x00FF);
        }
    }
    
    private void setValue(int value) {
        int k = (this.value.length > 4) ? 4 : this.value.length;
        fillValue((byte) 0);
        switch (k) {
            case 4:
                this.value[0] = (byte) (value & 0x00FF);
                this.value[1] = (byte) ((value >> 8) & 0x00FF);
                this.value[2] = (byte) ((value >> 16) & 0x00FF);
                this.value[3] = (byte) ((value >> 24) & 0x00FF);
                break;
            case 3:
                this.value[0] = (byte) (value & 0x00FF);
                this.value[1] = (byte) ((value >> 8) & 0x00FF);
                this.value[2] = (byte) ((value >> 16) & 0x00FF);
                break;
            case 2:
                this.value[0] = (byte) (value & 0x00FF);
                this.value[1] = (byte) ((value >> 8) & 0x00FF);
                break;
            case 1:
                this.value[0] = (byte) (value & 0x00FF);
                break;
            default:
                break;
        }
    }
    
    /**
     * 填充数据
     * @param b 
     */
    private void fillValue(byte b) {
        for (int i = 0; i < value.length; i++) {
            value[i] = b;
        }
    }
    
    /**
     * 时间字节数组转换为字符串
     * @param bs
     * @return
     */
    private String BCDToStr(byte bs[]) {
        String str = "";
        String temp;
        for (int i = 0; i < bs.length; i++) {
            temp = Integer.toHexString(bs[i]);
            if (temp.length() < 2) {
                temp = "0" + temp;
            }
            str = str + temp;
        }
        return str;
    }
    
    /**
     * 时间字符串转换为字节数组
     * @param bs
     * @return
     */
    private byte[] strToBCD(String value) {
        String temp = value.trim();
        int len = (temp.length() / 2) + (temp.length() % 2);
        byte rs[] = new byte[len];
        int k = 0;
        String str;
        while (temp.length() > 0) {
            if (temp.length() < 2) {
                str = temp;
                temp = "";
            } else {
                str = temp.substring(0, 2);
                temp = temp.substring(2);
            }
            rs[k++] = (byte) Integer.parseInt(str, 16);
        }
        return rs;
    }
}
