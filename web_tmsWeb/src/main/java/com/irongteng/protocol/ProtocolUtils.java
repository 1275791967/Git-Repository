/**
 * 
 */
package com.irongteng.protocol;

/**该类是个协议工具类，含有静态工厂方法，没有实例
 * @author liuqing
 *
 */
public class ProtocolUtils {

    /**
     * 
     */
    private ProtocolUtils() {
        
    }

    /**
     * 计算Crc校验和,CCITT:x16+x12+x5+1（0x11021）
     * @param data:校验的字节流
     * @return 返回CRC校验和
     */
    public static short calculateCrc16(byte[] data) {
        short crc = 0;
        for (int j = 0; j < data.length; j++) {
            for (byte i = (byte) 0x80; i != 0; i = (byte) ((i & 0xFF) >>> 1)) {
                if ((crc & 0x8000) != 0) {
                    // 余式CRC乘以2再求CRC
                    crc = (short) (crc * 2);
                    crc = (short) (crc ^ 0x1021);
                } else {
                    crc = (short) (crc * 2);
                }
                if ((data[j] & i) != 0) {
                    crc = (short) (crc ^ 0x1021); // 再加上本位的CRC
                }
            }
        }
        return crc;
    }

    /**
     * 计算从beginIndex到endIndex的Crc校验和,CCITT:x16+x12+x5+1（0x11021）
     * @param data:校验的字节流
     * @param beginIndex 起始位置
     * @param endIndex 结束位置
     * @return 返回CRC校验和
     */
    public static short calculateCrc16(byte[] data, int beginIndex, int endIndex) {
        if (data == null)
            return 0;
        if (beginIndex < 0)
            beginIndex = 0;
        if (endIndex < 0)
            endIndex = 0;
        if (endIndex > data.length)
            endIndex = data.length;
        if (endIndex <= beginIndex)
            return 0;
        byte[] tempbs = new byte[endIndex - beginIndex+1];
        int index = 0;
        for (int i = beginIndex; i <= endIndex; i++) {
            tempbs[index++] = data[i];
        }
        return calculateCrc16(tempbs);
    }

    /**
     * 协议中规定APA协议必须对发送的数据包进行转义处理
     * @param bs 需要转义的字节流
     * @return  返回转义后的字节流
     */
    public static byte[] escape(byte[] bs) {
        int count = 0;
        for(int i=0;i<bs.length;i++){
            if(bs[i] == 0x5E || bs[i] == 0x7E){
                count++;
            }
        }
        if(count <= 0){
            return bs;
        }
        byte[] result = new byte[bs.length+count];
        int index = 0;
        for(int i=0;i<bs.length;i++){
            //将0x5E转换成0x5E、0x5D
            if(bs[i] == 0x5E){
                result[index++] = 0x5E;
                result[index++] = 0x5D;
                continue;
            }
            //将0x7E转换成0x5E、0x7D
            if(bs[i] == 0x7E){
                result[index++] = 0x5E;
                result[index++] = 0x7D;
                continue;
            }
            result[index++] = bs[i];
        }
        //返回转义后的字节流
        return result;
    }

    /**
     * 协议中规定APA协议必须对接收的数据包进行反转义处理
     * @param bs 需要反转义处理的字节流
     * @return 返回反转义处理的字节流
     */
    public static byte[] eliminateEscapeChar(byte[] bs) {
        int count = 0;
        for(int i=0;i<bs.length;i++){
            if(bs[i] == 0x5E){
                count++;
            }
        }
        if(count <= 0){
            return bs;
        }
        byte[] result = new byte[bs.length-count];
        count = 0;
        for(int i=0;i<bs.length;i++){
            if(bs[i] == 0x5E){
                i++;
                //将0x5E、0x5D转换成0x5E
                if(bs[i] == 0x5D){
                    result[count++]=0x5E;
                }
                //将0x5E、0x7D转换成0x7E
                else if(bs[i] == 0x7D){
                    result[count++]=0x7E;
                }
            }
            else{
                result[count++]=bs[i];
            }
        }
        //返回反转义后的字节流
        return result;
    }

    /**
     * 把一个字节拆分为两个字节，如0x1A拆分为"1A"，供splitBytes方法调用
     * @param b:要拆分的字节
     * @return 返回拆分的字节
     */
    private static byte[] splitByte(byte b) {
        //8比特的16进制数被划分为高4bits和低4bits。对于高4bits和低4bits，若其数值为0x00-0x09，则加上0x30传输，若其数值为0x0A-0x0F，则加上0x37传输
        byte highbit=(byte)((b&0xF0)>>4);
        byte lowbit=(byte)(b&0x0F);
        if(highbit<0x0A){
            highbit+=0x30;
        }
        else{
            highbit+=0x37;
        }
        if(lowbit<0x0A){
            lowbit+=0x30;
        }
        else{
            lowbit+=0x37;
        }
        byte[] result = new byte[2];
        result[0]=highbit;
        result[1]=lowbit;
        //返回
        return result;
    }

    /**
     * 把单字节流拆分回2字节流
     * @param bs:要拆分的字节流
     * @return 返回拆分后的字节流
     */
    public static byte[] splitBytes(byte[] bs) {
        //循环调用splitByte方法对字节流中的每个字节进行二字节拆分
        byte[] result = new byte[bs.length*2];
        for(int i=0;i<bs.length;i++){
            byte[] temp=splitByte(bs[i]);
            result[i*2]=temp[0];
            result[i*2+1]=temp[1];
        }
        return result;
    }

    /**
     * 把两个字节合并为一个字节:如"1A"合并为0x1A,是splitByte的逆过程
     * @param high:高4比特位字节
     * @param low:低4比特位字节
     * @return <tt></tt>返回合并后的字节
     */
    public static byte combineTwoByte(byte high, byte low) {
        //对于高4bits和低4bits，若其数值为0x00-0x09，则减去0x30传输，若其数值为0x0A-0x0F，则减去0x37
        if(high <= 0x39){
            high-=0x30;
        }
        else{
            high-=0x37;
        }
        if(low <=0x39){
            low-=0x30;
        }
        else{
            low-=0x37;
        }
        //将两个字节组合成一个字节
        return (byte)((high<<4)+low);
    }

    /**
     * 把二字节流合并为单字节流，可以看作是splitBytes的逆过程
     * @param bs:合并的字节流
     * @return 返回合并后的字节流
     */
    public static byte[] combineBytes(byte[] bs) {
        //循环调用combineTwoByte方法对字节流中的每两个个字节进行合并
        byte[] result = new byte[bs.length/2];
        for(int i=0;i<bs.length/2;i++){
            result[i]=combineTwoByte(bs[i*2],bs[i*2+1]);
        }
        return result;
        
    }
    
    public static byte[] combineBytes(byte[] bs,int startIndex,int endIndex) {
        byte[] bytes =new byte[endIndex-startIndex+1];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = bs[startIndex+i];
        }
        return combineBytes(bytes);
    }

    /**
     * 将字节流转换成16进制字符串
     * @param data 字节流
     * @return 16进制字符串
     */
    public static String bytes2Hex(byte[] data) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            String str = Integer.toHexString(data[i] & 0xFF);
            if (str.length() <= 1)
                str = "0" + str;
            sb.append(str + " ");
        }
        return sb.toString().toUpperCase();
    }
    /**
     * 将16进制字符串转换成字节流
     * @param str 16进制字符串
     * @return 字节流
     */
    public static byte[] hex2Bytes(String str) {
        str=str.trim();
        str=str.replace(" ","");
        byte[] bytes = new byte[str.length()/2];
        int index = 0;
        for (int i = 0; i < str.length(); i++)
        {
            try
            {
                bytes[index++] = (byte)Integer.parseInt(str.substring(i,i+2),16);
            }
            catch(Exception e)
            {
                return new byte[0];
            }
            i++;
        }
        return bytes;
    }

    /**
     * 将字符串转换成16进制字符串
     * @param text 需要转换的字符串
     * @return 16进制字符串
     */
    public static String Str2Hex(String text) {
        StringBuffer sb = new StringBuffer();
        byte[] data;
        try {
            data = text.getBytes("ISO-8859-1");
        } catch (Exception e) {
            data = new byte[0];
        }

        for (int i = 0; i < data.length; i++) {
            String str = Integer.toHexString(data[i] & 0xFF);
            if (str.length() <= 1)
                str = "0" + str;
            sb.append(str + " ");
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 打印字节流
     * @param bs
     */
    public static void println(byte[] bs) {
        if (bs == null)
            System.out.println("null");
        else {
            for (int i = 0; i < bs.length; i++) {
                String str = Integer.toHexString(bs[i]);
                if (str.length() == 1) {
                    str = "0" + str;
                } else if (str.length() > 2) {
                    str = str.substring(str.length() - 2);
                }
                System.out.print(str.toUpperCase() + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * 从收到的数据中分离出完整的数据包，防止数据包前后有其他字符
     * 
     * @param dataPackage
     *            从直放站来的数据
     * @param startEndTag
     *            数据包的开始结束标志       
     * @return 返回协议数据包
     */
    public static byte[] filter(byte[] dataPackage,byte startEndTag) {
        try{
            // 从收到的数据中分离出完整的数据包
            int head = -1;
            int tail = -1;
            for (int i = 0; i < dataPackage.length; i++) {
                if (dataPackage[i] == startEndTag) {
                    if (head < 0) {
                        //由于APA及APC层可能出现0x21,并且分割数据时是先判断是否是APB,所以处理一下(!0201)
                        //是!0201的情况
                        if(startEndTag == 0x21 &&dataPackage[i+1] == 48 && dataPackage[i+2] == 50 && dataPackage[i+3] == 48 && dataPackage[i+4] == 49)
                        {
                            head = i;
                        }
                        else if(startEndTag == 0x7E)//包头为7E
                        {
                            head = i;
                        }else if(startEndTag == 0x58) //联通早期协议包头
                        {
                            head =i;
                        }
                    } else {
                        tail = i;
                        break;
                    }
                }
            }
            if (tail > 0) {
                int k = 0;
                byte[] bs = new byte[tail - head + 1];
                for (int i = head; i <= tail; i++) {
                    bs[k++] = dataPackage[i];
                }
                return bs;
            }
            return null;
        }
        catch(Exception e){
            return null;
        }
    }
}
