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
import org.junit.Test;
import com.irongteng.protocol.common.FddParamValueObject;
import com.irongteng.protocol.common.FddProtocol;

/**
 * An UDP client taht just send thousands of small messages to a UdpServer. 
 * 
 * This class is used for performance test purposes. It does nothing at all, but send a message
 * repetitly to a server.
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class UdpSimulatorFddTest{
    
    @Test
    public void testDataReport() throws Exception {
        
        UdpSimulator client = new UdpSimulator(5070);
        client.start();
        
        for (int i = 0; i <= 4; i++) {
            
            Thread.sleep(2000);
            FddParamValueObject param = new FddParamValueObject();
            
            param.setType("03");
            param.setProtocolID("24");
            param.setImsi("460018087949962");
            param.setDeviceName("GSM109");
            param.setCityCode("755");
            
            FddProtocol proto = new FddProtocol();
            byte[] data = proto.encode(param);
            
            FddParamValueObject param2 = new FddParamValueObject();
            proto.decode(data, param2);
            System.out.println(param2);
            
            client.send("localhost",5071, data);// 发送消息 这里是发送字节数组的重点
        }
        
        client.close();
    }
}
