package com.irongteng.comm.coder;

import com.irongteng.protocol.ProtocolConst;
import dwz.common.util.StringUtils;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//分包发包解码器
public class CommonProtocolDecoder extends CumulativeProtocolDecoder {
    
    public static Logger log = LoggerFactory.getLogger(CommonProtocolDecoder.class);
    
    private final Charset charset;

    public CommonProtocolDecoder(Charset charset) {
        this.charset = charset;
    }
    
    @Override
    protected boolean doDecode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput out) throws Exception {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        log.debug("in.remaining : " + buffer.remaining());
        
        if (buffer.remaining() >= 15){ //有数据时，读取前4字节判断消息长度 
            //标记当前位置，以便reset
            buffer.mark();
            //获取字节流长度
            int size = buffer.getInt();//读取4字节 
            log.debug("buffersize : " + size);
            //版本号
            byte version = buffer.get();
            buffer.getInt();
            buffer.getShort();
            short verCode = buffer.getShort();
            byte commandID = buffer.get();
            byte answerTag = buffer.get();
            //System.out.println("size: " + size + " version:" + version + " verCode:" + verCode +  " command:" + commandID + " answerTag:" + answerTag);
            //重置位置到当前位置
            buffer.reset();
            
            if (size > buffer.remaining() || size < 15) { //如果消息内容不够，则继续接收，相当于不读取size 
                if (size >= 15 && ((version==ProtocolConst.VERSION_V2 && verCode==0) || (version==ProtocolConst.VERSION_V3 && verCode!=0)) 
                        && answerTag==0 
                        && commandID!=0) {
                    return false;//父类接收新数据，以拼凑成完整数据 
                }
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                log.info("收到非法数据:" + StringUtils.bytes2HexString(data));
                return true;
                /*
                String key = "receiveCount";
                int count = 0;
                if (session.containsAttribute(key)) { 
                    count = (int)session.getAttribute(key);
                    count++;
                     //如果接收次数低于三次，则可以继续接收，否则可以认为数据非法，放弃数据接收
                    if (count > 3) {
                        session.removeAttribute(key);
                        return true;
                    }
                }
                session.setAttribute(key, count);
                */
            } else { 
                byte[] bytes = new byte[size];  
                buffer.get(bytes, 0, size);
                out.write(IoBuffer.wrap(bytes));
                
                if(buffer.remaining() > 0){//如果读取内容后还粘了包，就让父类再重读  一次，进行下一次解析 
                    return true; 
                } 
             } 
        } 
        return false;//处理成功，让父类进行接收下个包 
    }
}