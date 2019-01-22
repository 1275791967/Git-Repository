package com.irongteng.protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Imsi {
	
	public static final int CMCC_NO_134 = 134; 
    public static final int CMCC_NO_147 = 147; //3G上网卡
    public static final int CMCC_NO_150 = 150;
    public static final int CMCC_NO_151 = 151;
    public static final int CMCC_NO_152 = 152;
    public static final int CMCC_NO_157 = 157;
    public static final int CMCC_NO_158 = 158;
    public static final int CMCC_NO_159 = 159;
    public static final int CMCC_NO_178 = 178;
    public static final int CMCC_NO_182 = 182;
    public static final int CMCC_NO_183 = 183;
    public static final int CMCC_NO_184 = 184;
    public static final int CMCC_NO_187 = 187;
    public static final int CMCC_NO_188 = 188;

    public static final int CUCC_NO_130 = 130; //2G
    public static final int CUCC_NO_131 = 131; //2G
    public static final int CUCC_NO_132 = 132; //2G
    public static final int CUCC_NO_155 = 155; //3G
    public static final int CUCC_NO_156 = 156; //3G
    public static final int CUCC_NO_185 = 185; //3G
    public static final int CUCC_NO_186 = 186; //3G
    public static final int CUCC_NO_176 = 176; //暂无
    
    public static final int CTCC_NO_133 = 133; //2G
    public static final int CTCC_NO_153 = 153; //2G
    public static final int CTCC_NO_177 = 177;
    public static final int CTCC_NO_180 = 180; //3G
    public static final int CTCC_NO_181 = 181; //3G
    public static final int CTCC_NO_1700 = 1700;
    public static final int CTCC_NO_10649 = 10649;
    
    
	private static String s134 = "^460020(\\d)(\\d{3})\\d+";  //cmcc
	private static String s13x = "^46000(\\d{3})([0,1,2,3,4])(\\d)\\d+"; //cmcc
	private static String s13x0 = "^46000(\\d{3})([5,6,7,8,9])\\d+";     //cmcc
	private static String s147 = "^460079(\\d)(\\d{3})\\d+";  //cmcc
	private static String s150 = "^460023(\\d)(\\d{3})\\d+";  //cmcc
	private static String s151 = "^460021(\\d)(\\d{3})\\d+";  //cmcc
	private static String s152 = "^460022(\\d)(\\d{3})\\d+";  //cmcc
	private static String s157 = "^460077(\\d)(\\d{3})\\d+";  //cmcc
	private static String s158 = "^460028(\\d)(\\d{3})\\d+";  //cmcc
	private static String s159 = "^460029(\\d)(\\d{3})\\d+";  //cmcc
	private static String s178 = "^460075(\\d)(\\d{3})\\d+";  //cmcc
	private static String s182 = "^460026(\\d)(\\d{3})\\d+";  //cmcc
	private static String s183 = "^460025(\\d)(\\d{3})\\d+";  //cmcc
	private static String s184 = "^460024(\\d)(\\d{3})\\d+";  //cmcc
	private static String s187 = "^460027(\\d)(\\d{3})\\d+";  //cmcc
	private static String s188 = "^460078(\\d)(\\d{3})\\d+";  //cmcc
	private static String s1705 = "^460070(\\d)(\\d{3})\\d+"; //cmcc 移动虚拟运营商号段
	
	private static String s130 = "^46001(\\d{3})(\\d)[0,1]\\d+"; //cucc
	private static String s131 = "^46001(\\d{3})(\\d)9\\d+";  //cucc
	private static String s132 = "^46001(\\d{3})(\\d)2\\d+";  //cucc
	private static String s155 = "^46001(\\d{3})(\\d)4\\d+";  //cucc
	private static String s156 = "^46001(\\d{3})(\\d)3\\d+";  //cucc
	private static String s185 = "^46001(\\d{3})(\\d)5\\d+";  //cucc
	private static String s186 = "^46001(\\d{3})(\\d)6\\d+";  //cucc
	private static String s145 = "^46001(\\d{3})(\\d)7\\d+";  //cucc wcdma上网卡专用端
	private static String s170x = "^46001(\\d{3})(\\d)8\\d+"; //cucc联通虚拟运营商号段
	
	// 电信的，下面的还没有找到规则
	private static String st180 = "^46003(\\d)(\\d{3})7\\d+";  //ctcc
	private static String st153 = "^46003(\\d)(\\d{3})8\\d+";  //ctcc
	private static String st189 = "^46003(\\d)(\\d{3})9\\d+";  //ctcc
	
	public static String getMobileAll(String imsi) {

		String[] result = compile(s130, imsi);
		if (result != null && result.length == 2) {
			return "130" + result[1] + result[0];
		}
		result = compile(s131, imsi);
		if (result != null && result.length == 2) {
			return "131" + result[1] + result[0];
		}
		result = compile(s132, imsi);
		if (result != null && result.length == 2) {
			return "132" + result[1] + result[0];
		}
		result = compile(s134, imsi);
		if (result != null && result.length == 2) {
			return "134" + result[0] + result[1];
		}
		result = compile(s13x0, imsi);
		if (result != null && result.length == 2) {
			return "13" + result[1] + "0" + result[0];
		}
		result = compile(s13x, imsi);
		if (result != null && result.length == 3) {
			return "13" + (Integer.parseInt(result[1]) + 5) + result[2] + result[0];
		}
		result = compile(s150, imsi);
		if (result != null && result.length == 2) {
			return "150" + result[0] + result[1];
		}
		result = compile(s151, imsi);
		if (result != null && result.length == 2) {
			return "151" + result[0] + result[1];
		}
		result = compile(s152, imsi);
		if (result != null && result.length == 2) {
			return "152" + result[0] + result[1];
		}
		result = compile(s155, imsi);
		if (result != null && result.length == 2) {
			return "155" + result[1] + result[0];
		}
		result = compile(s156, imsi);
		if (result != null && result.length == 2) {
			return "156" + result[1] + result[0];
		}
		result = compile(s157, imsi);
		if (result != null && result.length == 2) {
			return "157" + result[0] + result[1];
		}
		result = compile(s158, imsi);
		if (result != null && result.length == 2) {
			return "158" + result[0] + result[1];
		}
		result = compile(s159, imsi);
		if (result != null && result.length == 2) {
			return "159" + result[0] + result[1];
		}
		result = compile(s147, imsi);
		if (result != null && result.length == 2) {
			return "147" + result[0] + result[1];
		}
		result = compile(s185, imsi);
		if (result != null && result.length == 2) {
			return "185" + result[1] + result[0];
		}
		result = compile(s186, imsi);
		if (result != null && result.length == 2) {
			return "186" + result[1] + result[0];
		}
		result = compile(s187, imsi);
		if (result != null && result.length == 2) {
			return "187" + result[0] + result[1];
		}
		result = compile(s188, imsi);
		if (result != null && result.length == 2) {
			return "188" + result[0] + result[1];
		}
		result = compile(s1705, imsi);
		if (result != null && result.length == 2) {
			return "170" + result[0] + result[1];
		}
		result = compile(s170x, imsi);
		if (result != null && result.length == 2) {
			return "170" + result[1] + result[0];
		}
		result = compile(s178, imsi);
		if (result != null && result.length == 2) {
			return "178" + result[0] + result[1];
		}
		result = compile(s145, imsi);
		if (result != null && result.length == 2) {
			return "145" + result[1] + result[0];
		}
		result = compile(s182, imsi);
		if (result != null && result.length == 2) {
			return "182" + result[0] + result[1];
		}
		result = compile(s183, imsi);
		if (result != null && result.length == 2) {
			return "183" + result[0] + result[1];
		}
		result = compile(s184, imsi);
		if (result != null && result.length == 2) {
			return "184" + result[0] + result[1];
		}
		result = compile(st180, imsi);
		if (result != null && result.length == 2) {
			return "180" + result[0] + result[1];
		}
		result = compile(st153, imsi);
		if (result != null && result.length == 2) {
			return "153" + result[0] + result[1];
		}
		result = compile(st189, imsi);
		if (result != null && result.length == 2) {
			return "189" + result[0] + result[1];
		}
		return "";
	}
	
	private static String[] compile(String reg, String imsi) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(imsi);

		if (matcher.find()) {
			String[] sArr = new String[matcher.groupCount()];
			for (int i = 0; i < matcher.groupCount(); i++) {
				sArr[i] = matcher.group(i + 1);
			}
			return sArr;
		}
		return null;
	}
	/**
     * 判断mac地址是否合法，
     * 要求连接符必须为冒号
     * 
     * @author jwang
     * @date 2013.4.19
     * @param macAddress
     * @return 
     */
    private static boolean isMacAddress(String macAddress){
        String reg = "^([0-9a-fA-F]){2}([:-][0-9a-fA-F]{2}){5}";
        return Pattern.compile(reg).matcher(macAddress).find();
    }
    private static boolean isNumber(String num){
        String reg = "^46000(\\d{3})([0-8])(\\d)\\d+";
        return Pattern.compile(reg).matcher(num).find();
    }
    
	private static boolean isHexNumeric(String macAddress){
        String reg = "^([0-9a-fA-F]{2})+$";
        return Pattern.compile(reg).matcher(macAddress).find();
    }
	
	public static void main(String[] args) {
		System.out.println(getMobileAll("460015517507343"));
		
		System.out.println("mac:" + isMacAddress("46-0F-0A-51-85-0F"));
		System.out.println("imsi:" + isNumber("460005518507343"));
		System.out.println("is hex number:" + isHexNumeric("460F0A51850F"));
		System.out.println("is hex number:" + isHexNumeric("46-0F-0A-51-85-0F"));
		System.out.println("is hex number:" + isHexNumeric("46 0F 0A 51 85 0F"));
		System.out.println("imsi:" + Integer.parseInt("0460005"));
		System.out.println("test" + System.getProperty("compile.debug"));
	}
	
}
