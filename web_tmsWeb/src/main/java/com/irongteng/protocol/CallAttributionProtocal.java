package com.irongteng.protocol;

public class CallAttributionProtocal {
    
    public static final String CMCC_00 = "00"; //移动标识
    public static final String CMCC_02 = "02"; //移动标识
    public static final String CMCC_07 = "07"; //移动标识
    
    public static final String CUCC_01 = "01";  //联通标识
    public static final String CTCC_03 = "03";  //电信标识
    
    public static final int CMCC_NO_134 = 134;
    public static final int CMCC_NO_147 = 147;
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
    
    public static final int CUCC_NO_130 = 130;
    public static final int CUCC_NO_131 = 131;
    public static final int CUCC_NO_132 = 132;
    public static final int CUCC_NO_156 = 156;
    public static final int CUCC_NO_155 = 155;
    public static final int CUCC_NO_186 = 186;
    public static final int CUCC_NO_185 = 185; //暂无
    public static final int CUCC_NO_176 = 176; //暂无
    
    public static final int CTCC_NO_153 = 153;
    public static final int CTCC_NO_133 = 133;
    public static final int CTCC_NO_180 = 180;
    public static final int CTCC_NO_10649 = 10649;
    public static final int CTCC_NO_181 = 181;
    public static final int CTCC_NO_177 = 177;
    public static final int CTCC_NO_1700 = 1700;
    
    private Integer commOperator;
    
    /**
     * 解析IMSI为手机号码
     * @param imsi
     * @return
     */
    public String decode(String imsi) {
        if (imsi==null || "".equals(imsi)) {
            return null;
        }
        if ( imsi.trim().length()!=15) {
            return null;
        }
        
        imsi = imsi.trim();
        StringBuilder sb = new StringBuilder(); // 用于存储手机号码前8位
        String operator = imsi.substring(3, 5);
        
        if (operator.equals(CMCC_00) || operator.equals(CMCC_02) || operator.equals(CMCC_07)) { //移动
            this.setCommOperator(1);
            
            if (operator.equals(CMCC_00)) {
                int st = Integer.parseInt(imsi.substring(8, 9));
                if (st==5 || st==6 || st==7 || st==8 ||st==9) {
                    sb.append("13").append(st).append("0").append(imsi.substring(5, 8));
                } else {
                    sb.append("13").append(st + 5).append(imsi.substring(9, 10)).append(imsi.substring(5, 8));
                }
            } else if (operator.equals(CMCC_02)) {
                
                String noType = imsi.substring(5, 6);
                switch (noType) {
                    case "0":
                        sb.append(CMCC_NO_134);
                        break;
                    case "8":
                        sb.append(CMCC_NO_158);
                        break;
                    case "9":
                        sb.append(CMCC_NO_159);
                        break;
                    case "3":
                        sb.append(CMCC_NO_150);
                        break;
                    case "1":
                        sb.append(CMCC_NO_151);
                        break;
                    case "2":
                        sb.append(CMCC_NO_152);
                        break;
                    case "7":
                        sb.append(CMCC_NO_187);
                        break;
                    case "5":
                        sb.append(CMCC_NO_183);
                        break;
                    case "4":
                        sb.append(CMCC_NO_184);
                        break;
                    case "6":
                        sb.append(CMCC_NO_182);
                        break;
                    default:
                        break;
                }
                sb.append(imsi.substring(6, 10));
                
            } else if (operator.equals(CMCC_07)) {
                String noType = imsi.substring(5, 6);
                if (noType.equals("7")) {
                    sb.append(CMCC_NO_157);
                } else if(noType.equals("8")) {
                    sb.append(CMCC_NO_188);
                } else if(noType.equals("9")) {
                    sb.append(CMCC_NO_147);
                }
                sb.append(imsi.substring(6, 10));
            }
            
        } else if(operator.equals(CUCC_01)) {  //联通
            
            this.setCommOperator(2);
            
            int a = Integer.parseInt(imsi.substring(9, 10));
            switch(a) {
            case 0: 
                sb.append(CUCC_NO_130);
                break;
            case 1:
                sb.append(CUCC_NO_130);
                break;
            case 9:
                sb.append(CUCC_NO_131);
                break;
            case 2:
                sb.append(CUCC_NO_132);
                break;
            case 3:
                sb.append(CUCC_NO_156);
                break;
            case 4:
                sb.append(CUCC_NO_155);
                break;
            case 6:
                sb.append(CUCC_NO_186);
                break;
            case 7:
                sb.append(CUCC_NO_185);
                break;
            case 8:
                sb.append(CUCC_NO_176);
                break;
            }
            sb.append(imsi.substring(8, 9)).append(imsi.substring(5, 8));
            
        } else if(operator.equals(CTCC_03)) {  //电信
            this.setCommOperator(3);
            
            int first = Integer.parseInt(imsi.substring(5, 6));
            int last = Integer.parseInt(imsi.substring(8, 9));
            if (first==6 &&(last>=0 && last<=9)) {
                sb.append(CTCC_NO_153).append(imsi.substring(6,8)).append(imsi.substring(9,11));
            } else {
                int type = Integer.parseInt(imsi.substring(5, 7));
                switch (type) {
                    case 9:
                        sb.append(CTCC_NO_133).append(imsi.substring(7,11));
                        break;
                    case 1:
                    case 2:
                    case 4:
                        sb.append(CTCC_NO_180).append(imsi.substring(7,11));
                        break;
                    case 3:
                        {
                            int subType = Integer.parseInt(imsi.substring(7, 9));
                            if (subType>=0 && subType<=9) {
                                sb.append(CTCC_NO_133).append(imsi.substring(7,11));
                            } else if (subType>=60 && subType<=70) {
                                sb.append(CTCC_NO_180).append(imsi.substring(7,11));
                            }
                            break;
                        }
                    case 7:
                        {
                            int subType = Integer.parseInt(imsi.substring(7, 9));
                            if (subType>=20 && subType<=31) {
                                sb.append(CTCC_NO_180).append(imsi.substring(7,11));
                            } else if (subType>=40 && subType<=44) {
                                sb.append(CTCC_NO_10649).append(imsi.substring(7,11));
                            } else {
                                sb.append(CTCC_NO_181).append(imsi.substring(7,11));
                            }
                            break;
                        }
                    case 8:
                    case 10:
                        sb.append(CTCC_NO_181).append(imsi.substring(7,11));
                        break;
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                        sb.append(CTCC_NO_177).append(imsi.substring(7,11));
                        break;
                    case 12:
                    case 74:
                        {
                            int subType = Integer.parseInt(imsi.substring(7, 9));
                            if (type == 12) {
                                if ((subType>=18 && subType<=26) || (subType>=40 && subType<=51) || (subType>=1 && subType<=16)) {
                                    sb.append(CTCC_NO_181).append(imsi.substring(7,11));
                                } else {
                                    sb.append(CTCC_NO_177).append(imsi.substring(7,11));
                                }
                            } else {
                                if (subType>=10 && subType<=51) {
                                    sb.append(CTCC_NO_1700).append(imsi.substring(8,11));
                                } else {
                                    sb.append(CTCC_NO_177).append(imsi.substring(7,11));
                                }
                            }
                            break;
                        }
                    default:
                        break;
                }
            }
            
        } else {
            return null;
        }
        sb.append(imsi.substring(11,15));
        return sb.toString();
    }
    
    /**
     * 将手机号码转换为IMSI
     * @param phone
     * @return
     */
    public String encode(String phone) {
        if (phone==null || "".equals(phone)) {
            return null;
        }
        if (phone.trim().length()!=15) {
            return null;
        }
        
        phone = phone.trim();
        StringBuilder sb = new StringBuilder(); // 用于存储手机号码前8位
        String operator = phone.substring(3, 5);
        
        switch (operator) {
            case CMCC_00:
            case CMCC_02:
            case CMCC_07:
                //移动
                this.setCommOperator(1);
                switch (operator) {
                    case CMCC_00:
                        int st = Integer.parseInt(phone.substring(8, 9));
                        if (st==5 || st==6 || st==7 || st==8 ||st==9) {
                            sb.append("13").append(st);
                            sb.append("0").append(phone.substring(5, 8));
                        } else {
                            sb.append("13").append(st + 5);
                            sb.append(phone.substring(9, 10)).append(phone.substring(5, 8));
                        }
                        break;
                    case CMCC_02:
                    {
                        String noType = phone.substring(5, 6);
                        switch (noType) {
                            case "0":
                                sb.append(CMCC_NO_134);
                                break;
                            case "8":
                                sb.append(CMCC_NO_158);
                                break;
                            case "9":
                                sb.append(CMCC_NO_159);
                                break;
                            case "3":
                                sb.append(CMCC_NO_150);
                                break;
                            case "1":
                                sb.append(CMCC_NO_151);
                                break;
                            case "2":
                                sb.append(CMCC_NO_152);
                                break;
                            case "7":
                                sb.append(CMCC_NO_187);
                                break;
                            case "5":
                                sb.append(CMCC_NO_183);
                                break;
                            case "4":
                                sb.append(CMCC_NO_184);
                                break;
                            case "6":
                                sb.append(CMCC_NO_182);
                                break;
                            default:
                                break;
                        }   sb.append(phone.substring(6, 10));
                        break;
                    }
                    case CMCC_07:
                    {
                        String noType = phone.substring(5, 6);
                        switch (noType) {
                            case "7":
                                sb.append(CMCC_NO_157);
                                break;
                            case "8":
                                sb.append(CMCC_NO_188);
                                break;
                            case "9":
                                sb.append(CMCC_NO_147);
                                break;
                            default:
                                break;
                        }
                        sb.append(phone.substring(6, 10));
                        break;
                    }
                    default:
                        break;
                }
                break;
            case CUCC_01:
                //联通
                this.setCommOperator(2);
                int a = Integer.parseInt(phone.substring(9, 10));
                switch(a) {
                    case 0:
                        sb.append(CUCC_NO_130);
                        break;
                    case 1:
                        sb.append(CUCC_NO_130);
                        break;
                    case 9:
                        sb.append(CUCC_NO_131);
                        break;
                    case 2:
                        sb.append(CUCC_NO_132);
                        break;
                    case 3:
                        sb.append(CUCC_NO_156);
                        break;
                    case 4:
                        sb.append(CUCC_NO_155);
                        break;
                    case 6:
                        sb.append(CUCC_NO_186);
                        break;
                    case 7:
                        sb.append(CUCC_NO_185);
                        break;
                    case 8:
                        sb.append(CUCC_NO_176);
                        break;
                }
                sb.append(phone.substring(8, 9)).append(phone.substring(5, 8));
                break;
            case CTCC_03:
                //电信
                this.setCommOperator(3);
                int first = Integer.parseInt(phone.substring(5, 6));
                int last = Integer.parseInt(phone.substring(8, 9));
                if (first==6 &&(last>=0 && last<=9)) {
                    sb.append("153").append(phone.substring(6,8)).append(phone.substring(9,11));
                } else {
                    int type = Integer.parseInt(phone.substring(5, 7));
                    switch (type) {
                        case 9:
                            sb.append("133").append(phone.substring(7,11));
                            break;
                        case 1:
                        case 2:
                        case 4:
                            sb.append("180").append(phone.substring(7,11));
                            break;
                        case 3:
                            {
                                int subType = Integer.parseInt(phone.substring(7, 9));
                                if (subType>=0 && subType<=9) {
                                    sb.append("133").append(phone.substring(7,11));
                                } else if (subType>=60 && subType<=70) {
                                    sb.append("180").append(phone.substring(7,11));
                                }
                                break;
                            }
                        case 7:
                            {
                                int subType = Integer.parseInt(phone.substring(7, 9));
                                if (subType>=20 && subType<=31) {
                                    sb.append("180").append(phone.substring(7,11));
                                } else if (subType>=40 && subType<=44) {
                                    sb.append("10649").append(phone.substring(7,11));
                                } else {
                                    sb.append("181").append(phone.substring(7,11));
                                }
                                break;
                            }
                        case 8:
                        case 10:
                            sb.append("181").append(phone.substring(7,11));
                            break;
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                            sb.append("177").append(phone.substring(7,11));
                            break;
                        case 12:
                        case 74:
                            {
                                int subType = Integer.parseInt(phone.substring(7, 9));
                                if (type == 12) {
                                    if ((subType>=18 && subType<=26) || (subType>=40 && subType<=51) || (subType>=1 && subType<=16)) {
                                        sb.append("181").append(phone.substring(7,11));
                                    } else {
                                        sb.append("177").append(phone.substring(7,11));
                                    }
                                } else {
                                    if (subType>=10 && subType<=51) {
                                        sb.append("1700").append(phone.substring(8,11));
                                    } else {
                                        sb.append("177").append(phone.substring(7,11));
                                    }
                                }
                                break;
                            }
                            default:
                    break;
                }
            }
            break;
        default:
            return null;
        }
        sb.append(phone.substring(11,15));
        return sb.toString();
    }

    public int getCommOperator() {
        return commOperator;
    }

    public void setCommOperator(Integer commOperator) {
        this.commOperator = commOperator;
    }
}
