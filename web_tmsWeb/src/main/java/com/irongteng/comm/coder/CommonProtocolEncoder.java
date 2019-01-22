package com.irongteng.comm.coder;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class CommonProtocolEncoder implements ProtocolEncoder {
    private final Charset charset;

    public CommonProtocolEncoder(Charset charset) {
        this.charset = charset;
    }

    @Override
    public void encode(IoSession session, Object message,
            ProtocolEncoderOutput out) throws Exception {
        
        //super.encode(session, message, out);
        /*
        LoginMsg lm = (LoginMsg) message;
        IoBuffer buffer = IoBuffer.allocate(100, false).setAutoExpand(true);

        buffer.putShort(lm.getStartBit());
        buffer.put(lm.getPackageLen());
        buffer.put(lm.getProtocolNo());

        buffer.put(lm.getContent().getBytes(charset));

        buffer.putShort(lm.getSerialNumber());
        buffer.putShort(lm.getErrorCheck());
        buffer.putShort(lm.getEndBit());

        buffer.flip();
        out.write(buffer);
        */
    }
    
    @Override
    public void dispose(IoSession session) throws Exception {

    }

}