package com.irongteng.thirdinterface;

import org.apache.mina.core.session.IoSession;

import com.irongteng.protocol.common.CommonParamValueObject;

public class HttpQueryInfo {
    
    public String queryStr = null;
    public CommonParamValueObject param = null;
    
    public IoSession session = null;
    
}
