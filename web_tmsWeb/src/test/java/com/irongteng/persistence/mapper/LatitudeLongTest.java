//package com.irongteng.persistence.mapper;
//
//import java.util.Arrays;
//
//import org.junit.Test;
//
//import com.irongteng.Unit.Buffer;
//import com.irongteng.Unit.LatitudeLongUnit;
//
//public class LatitudeLongTest {
//    
//    @Test
//    public void LatitudeLongUnitTest(){
//        byte[] aa=new byte[20];
//         for(int i=0;i<aa.length;i++){
//             aa[i]='a';
//         }
//        
//        byte[]bb=new byte[20];
//        for(int i=0;i<bb.length;i++){
//            bb[i]='b';
//        }
//        byte[] cc=new byte[50];
//        for(int i=0;i<cc.length;i++){
//            cc[i]='c';
//        }
//        LatitudeLongUnit mobileloginUnit = new LatitudeLongUnit( );
//         mobileloginUnit.latitude=aa;
//         mobileloginUnit.longitude=bb;
//         Buffer buffer = mobileloginUnit.toBuffer();
//         System.out.println(Arrays.toString(buffer.getByte()));
//         
//        
//         byte[] data = {97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 98, 98, 98, 98, 98, 98, 98, 98, 98, 98, 98, 98, 98, 98, 98, 98, 98, 98, 98, 98, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//         Buffer buffer1 =new Buffer(data);
//         LatitudeLongUnit unit1 = new LatitudeLongUnit(buffer1);
//         System.out.println(Arrays.toString(unit1.latitude));
//         System.out.println(Arrays.toString(unit1.longitude));
//    }
//    
//
//}
