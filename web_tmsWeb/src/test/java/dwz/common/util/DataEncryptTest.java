/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dwz.common.util;

import java.util.Arrays;
import org.junit.Test;

import dwz.common.util.encrypt.DataEncrypt;

public class DataEncryptTest {
    
       
    @Test
    public void testParseDateTime() {
        byte[] dataXorKeys = {113, -102, 60, 20, 30, 64, 24, -34, 18, -108, -39, -49, -94, -87, -5, -21};
        String imsi = "460015716515760";
        short packageID = 9;
        
        byte[] imsis = DataEncrypt.getInstance().getIMSIEncryptData(imsi, packageID, dataXorKeys);
        System.out.println(Arrays.toString(imsis));
        
    }
}
