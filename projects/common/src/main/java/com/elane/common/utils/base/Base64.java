package com.elane.common.utils.base;

import java.io.UnsupportedEncodingException;

import sun.misc.*;  
/**
 *
 * @description
 *      base64加密解密
 * @author carl
 *
 * @version 创建时间：2016年11月22日 下午5:42:34
 * 
 **/
public class Base64 {  
    // 加密  
    @SuppressWarnings("restriction")
    public static String encode(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
  
    // 解密  
    @SuppressWarnings("restriction")
    public static String decode(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
}  