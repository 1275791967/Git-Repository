package com.irongteng.comm.coder;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

//编解码器生成工产
public class CommonProtocolCodecFactory implements ProtocolCodecFactory {
    private ProtocolEncoder encoder;
    private ProtocolDecoder decoder;

    public CommonProtocolCodecFactory() {
        this(Charset.forName("UTF-8"));
    }

    public CommonProtocolCodecFactory(Charset charset) {
        encoder = new CommonProtocolEncoder(charset);
        decoder = new CommonProtocolDecoder(charset);
    }
    
    @Override
    public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
        return decoder;
    }
    
    @Override
    public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
        return encoder;
    }

}