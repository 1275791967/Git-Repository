/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package com.irongteng.comm.simulator;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.irongteng.protocol.common.LTEParamValueObject;
import com.irongteng.protocol.common.LTEProtocol;

import dwz.common.util.StringUtils;

/**
 * An UDP client taht just send thousands of small messages to a UdpServer. 
 * 
 * This class is used for performance test purposes. It does nothing at all, but send a message
 * repetitly to a server.
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class UdpSimulatorTddTest{
    
    @Test
    public void testDataReport() throws Exception {
        
        UdpSimulator client = new UdpSimulator(9201);
        client.start();
        
        for (int i = 0; i <= 4; i++) {
            
            Thread.sleep(2000);
            LTEParamValueObject param = new LTEParamValueObject();
            
            param.setCommandID((byte)0xD5);
            param.setBodyLen((short)44);
            List<String> imsis = new ArrayList<>();
            imsis.add("4644398788589918");
            imsis.add("4643398788589916");
            param.setImsiList(imsis);
            
            
            LTEProtocol proto = new LTEProtocol();
            byte[] data = proto.encode(param);
            
            LTEParamValueObject param2 = new LTEParamValueObject();
            proto.decode(data, param2);
            System.out.println(param2);
            
            boolean state = client.send("127.0.0.1", 9202, data);// 发送消息 这里是发送字节数组的重点
            System.out.println("发送状态" + state + " 数据：" + StringUtils.bytes2HexString(data));
        }
        
        client.close();
    }
}
