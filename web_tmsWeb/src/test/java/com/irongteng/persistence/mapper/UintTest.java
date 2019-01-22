package com.irongteng.persistence.mapper;

public class UintTest {
    
        /*@Test
        public void CancelUnitTest(){
        CancelUnit unit = new CancelUnit( );
        unit.imsi= new Bytes("862381234567890", 20);
        unit.translateType = new Uint8(2);
        Buffer buffer = unit.toBuffer();
        System.out.println(Arrays.toString(buffer.getByte()));
            byte[] bs = {51, 57, 101, 100, 97, 51, 56, 50, 57, 52, 101, 100, 102, 56, 57, 52, 56, 97, 57, 50, 56, 50, 56, 52, 53, 52, 45, 68, 57, 55, 65, 45, 51, 67, 56, 68, 45, 49, 67, 70, 49, 0};
            System.out.println(bs.length);    
        }
        
        @Test
        public void ClosingUnitTest(){
        ClosingUnit unit = new ClosingUnit( );
        Buffer buffer = unit.toBuffer();
        System.out.println(Arrays.toString(buffer.getByte()));
        }
    
    
        @Test
        public void GetPhoneResultTest(){
          GetPhoneResultUnit unit = new GetPhoneResultUnit();
          unit.imsi = new Bytes("862435234567890",20);  
          unit.imei = new Bytes("862381234324590",20);
          unit.tmsi = new Uint32(4);
          unit.phone = new Bytes("1232167890",20);
          Buffer buffer = unit.toBuffer();
          System.out.println(Arrays.toString(buffer.getByte()));
          
        }
    
    
        @Test
        public void DeviceVerifyTest(){
          DeviceVerifyUnit unit = new DeviceVerifyUnit( );
          unit.firsLogin = new Uint8(2);
          unit.deviceType = new Uint8(2);
          unit.featureCode = new Bytes("862381234324590",20);
          Buffer buffer = unit.toBuffer();
          System.out.println(Arrays.toString(buffer.getByte()));
          
        }
    
        @Test
        public void LatitudeLongTest(){
        LatitudeLongUnit unit = new LatitudeLongUnit();
        unit.latitude = new Bytes("4342423432421",20);
        unit.longitude =new Bytes("5354234234234",20);
        Buffer buffer = unit.toBuffer();
        System.out.println(Arrays.toString(buffer.getByte()));
        
        }
    
    
        @Test
        public void TranslateUnitTest(){
            TranslateUnit uint = new TranslateUnit();
            uint.cmccChannels = new Uint8(2);
            uint.cmccNarmalChannels = new Uint8(2);
            uint.cdmaWaitTranslates = new Uint16(4);
            uint.unicomChannels= new Uint8(2);
            uint.unicomNarmalChannels = new Uint8(2);
            uint.unicomWaitTranslates = new Uint16(4);
            uint.cdmaChannels= new Uint8(2);
            uint.cdmaNarmalChannels = new Uint8(2);
            uint.cdmaWaitTranslates = new Uint16(4);
            uint.alarmState = new Uint32(4);
            Buffer buffer = uint.toBuffer();
            System.out.println(Arrays.toString(buffer.getByte()));
            
        }
    
        @Test
        public void MobileloginresultsTest(){
            
            MobileloginresultsUnit uint = new MobileloginresultsUnit();
            uint.loginResult = new Uint8(2);
            uint.localBalance = new Uint32(4);
            uint.thirdBalance = new Uint32(4);
            Buffer buffer = uint.toBuffer();
            System.out.println(Arrays.toString(buffer.getByte()));
        }

        @Test
        public void DeviceVerifyUnitTest(){
            DeviceVerifyUnit uint = new DeviceVerifyUnit();
            uint.firsLogin = new Uint8(2);
            uint.deviceType = new Uint8(2);
            uint.workStyle = new Uint8(2);
            uint.featureCode = new Bytes("8976555667",20);
            Buffer buffer = uint.toBuffer();
            System.out.println(Arrays.toString(buffer.getByte()));
        }
        
    
        @Test
        public void AuthenticationTest(){
            AuthenticationResultsUnit uint = new AuthenticationResultsUnit();
            uint.authentication = new Bytes("1212121212",20);
            Buffer buffer = uint.toBuffer();
            System.out.println(Arrays.toString(buffer.getByte()));
        }
        
    
        @Test
        public void AuthentTest(){
            AuthenticationUnit uint = new AuthenticationUnit();
            uint.publickey = new Bytes("1321312322",20);
            uint.authentication = new Bytes("1231234121",20);
            Buffer buffer = uint.toBuffer();
            System.out.println(Arrays.toString(buffer.getByte()));
                    
        }
    
        @Test
        public void GetPhoneUnitTest(){
            GetPhoneUnit uint = new GetPhoneUnit();
            uint.thridSwitch = new Uint8(2);
            uint.translateType = new Uint8(2);
            uint.imsi = new Bytes("12432422322",20);
            uint.imei = new Bytes("1234323121",20);
            uint.tmsi = new Uint32(4);
            uint.lac = new Uint16(2);
            uint.rssi = new Uint8(2);
            uint.first = new Uint8(2);
            uint.phone = new Bytes("13213323222",20);
            uint.latitude = new Bytes("1321332332",20);
            uint.longitude = new Bytes("1231323221",20);
            Buffer buffer = uint.toBuffer();
            System.out.println(Arrays.toString(buffer.getByte()));
            
        }
    
        @Test
        public void GsmAuthenResultsTest(){
            GsmAuthenResultsUnit uint = new GsmAuthenResultsUnit();
            uint.imsi = new Bytes("23232323",20);
            uint.sres = new Uint32(2);
            Buffer buffer = uint.toBuffer();
            System.out.println(Arrays.toString(buffer.getByte()));
            
        }
    
    
        @Test
        public void GsmAuthenticationTest(){
            GsmAuthenticationUnit uint = new GsmAuthenticationUnit();
            uint.imsi = new Bytes("23232311123",20);
            uint.translateType = new Uint8(2);
            uint.gsmRand = new Bytes("232323213",16);
            Buffer buffer = uint.toBuffer();
            System.out.println(Arrays.toString(buffer.getByte()));
        }
        
        
        @Test
        public void getPhoneAttributionTest(){
            AttributionBean bean = CommonParameterControl.getPhoneAttribution("13800911234");
            System.out.println(bean.toString());
//            new CommonParameterControl().dealTranslateCancel("87262136127312", null);
//            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//            new Timer("test111").schedule(new TimerTask() {
//                
//                @Override
//                public void run() {
//                    // TODO Auto-generated method stub
//                    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//                }
//            }, 1000, 1000);
            
            new CommonParameterControl().dealTranslateCancel("87262136127312", null);
        }*/
 }
