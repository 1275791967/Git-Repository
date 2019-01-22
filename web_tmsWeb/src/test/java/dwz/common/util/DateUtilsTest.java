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

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;

import dwz.common.util.time.DateUtils;

public class DateUtilsTest {
    
    Logger logger = Logger.getLogger(DateUtilsTest.class);
            
    @Test
    public void testGetStartTimeOfDate() {
        System.out.println(DateUtils.getStartTimeOfDate(new Date()));
    }
    
    @Test
    public void testGetEndTimeOfDate() {
        System.out.println(DateUtils.getEndTimeOfDate(new Date()));
    }
    /**
     * 将长的无分割的类似YYYYMMDDHHmmss字符串转换为"YYYY-MM-DD HH:mm:ss"时间格式字符串
     * 
     * @param longtime
     * @return
     */
    @Test
    public void testLongDateString2StandardDateString() {
        System.out.println(DateUtils.longDateString2StandardDateString("20150412121322"));
    }
    
    /**
     * 将标准时间格式字符串格式化为长字符串，的类似"YYYY-MM-DD HH:mm:ss"字符串转换为YYYYMMDDHHmmss时间格式字符串
     * 
     * @param longtime
     * @return
     */
    @Test
    public void testStandardStringDate2LongDateString() {
        System.out.println(DateUtils.standardStringDate2LongDateString("2015-04-12 12:13:22"));
    }
    /**
     * 将时间转换为长时间格式字符串
     * @param date
     * @return
     */
    @Test
    public void testFormatHpDate() {
        System.out.println(DateUtils.formatHpDate(new Date()));
    }

    /**
     * 将时间转换为长时间格式字符串
     * @param date
     * @return
     */
    @Test
    public void testParseHpDate() {
        String date = "20150312122516";
        System.out.println(DateUtils.parseHpDate(date));
    }
    
    /**
     * 日期转换为文件格式日期，用于文件时间标识
     * @param date
     * @return
     */
    @Test
    public void testFormatFileDate() {
        System.out.println(DateUtils.formatFileDate(new Date()));
    }
    
    /**
     * 文件名日期格式字符串yyyy_MM_dd_HH_mm_ss转换为日期，用于文件时间标识
     * @param date
     * @return
     */
    @Test
    public void testParseFileDate() {
        String date = "2015_03_12_12_25_16";
        System.out.println(DateUtils.parseFileDate(date));
    }

    /**
     * 获取标准时间
     * 
     * @param date
     * @return
     */
    @Test
    public void testFormatStandardDate() {
        System.out.println(DateUtils.formatStandardDate(new Date()));
    }
    /**
     * 将标准时间字符串格式化成时间对象 
     *     如2015-09-27 00:50:59
     * @param datetime
     * @return
     */
    @Test
    public void testParseStandardDate() {
        String date = "2015-03-12 12:25:16";
        System.out.println(DateUtils.parseStandardDate(date));
    }
    /**
     * 获取标准日期时间字符串 YYYY-MM-dd
     * 
     * @param date
     * @return
     */
    
    @Test
    public void testFormatDate() {
        System.out.println(DateUtils.formatDate(new Date()));
    }
    /**
     * 获取日期时间
     * @param date
     * @return
     */
    
    @Test
    public void testParseDate() {
        String date = "2015-03-12";
        System.out.println(DateUtils.parseDate(date));
    }
     /**
     * 指定格式格式化日期
     * 
     * @param date
     * @return
     */
    @Test
    public void testFormatDateTime() {
        System.out.println(DateUtils.formatDateTime(new Date(), "yyyy-MM-dd hh:mm:ss"));
        logger.info(DateUtils.formatDateTime(new Date(), "yyyy-MM-dd hh:mm:ss"));
    }
    
    /**
     * 解析时间字符串为指定格式的时间
     * @param date
     * @param pattern
     * @return
     */
    
    @Test
    public void testParseDateTime() {
        String date = "2015-03-12 12:25:16";
        System.out.println(DateUtils.parseDateTime(date, "yyyy-MM-dd hh:mm:ss"));
    }
}
