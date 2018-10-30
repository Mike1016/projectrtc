package com.daka.util;

/**
 * 静态常量
 */
public class Const
{
    
    public static final String LOGIN = "/login_toLogin.do"; // 登录地址
    
    public static final String APP_LOGIN = "/app/RegisterUser/login_toLogin"; // 登录地址
    
    public static final String NO_INTERCEPTOR_PATH =
        ".*/((login)|(logout)|(code)|(static)|(main)|(websocket)|(upload)).*"; // 不对匹配该值的访问路径拦截（正则）
    
    public static final String APP_PATH = ".*/((app)).*"; // 不对匹配该值的访问路径拦截（正则）
    
    // 成功
    public static final int SUCCESS = 0;
    
    // 失败
    public static final int ERROR = -1;
    
    //存放appuser
    public static final String APP_USER="APPUSER";
    
}
