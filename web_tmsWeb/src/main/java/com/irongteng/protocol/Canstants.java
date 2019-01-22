package com.irongteng.protocol;

public class Canstants {
    
    //翻译流程的翻译状态
    public final static int TRANS_STATUS_ING = 1;           //正在翻译
    public final static int TRANS_STATUS_CANCELED = 2;      //取消翻译
    public final static int TRANS_STATUS_SUCCESS = 3;       //翻译成功
    public final static int TRANS_STATUS_FAIL = 4;          //翻译失败
    public final static int TRANS_STATUS_SYSTEM = 5;        //系统翻译
    public final static int TRANS_STATUS_THIRD = 6;         //第三方翻译
    
    
    public final static int TYPE_DEVICE_VERIFY = 3;
    public final static int TYPE_INDENTIFY_VERIFY = 4;
    public final static int TYPE_INDENTIFY_VERIFY_RESULT = 5;
    
    
    public final static int TYPE_TRANSLATE_CANCEL = 6;
    
    
    //手机客户端登录结果
    public final static int ACCOUNT_LOGIN_SUCCESS = 0;   //登录成功
    public final static int MISS_ACCOUNT_NAME = 1;       //用户名不存在
    public final static int PASSWORD_ERROR = 2;          //密码错误
    
}
