package com.elane.common.utils.base;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/***
 * 加密解密工具类
 * @author hankin
 * 
 *
 */
public class DesUtils {

    /***
     * 解密算法
     * 1、UTF-8 转码
     * 2、异或
     * 3、BASE64解密
     * @param base64
     * @return
     */
    public static String decryptByBase64(String base64,String key){
        String source = "";
        try {
        	source = URLDecoder.decode(base64, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        source = DesUtils.enOrDe(source, key);
        source = DesUtils.decodeBASE64(source);
        return source;
    }

    /***
     * 加密算法
     * 1、BASE64加密
     * 2、异或
     * 3、UTF-8 转码
     * @param source
     * @return
     */
    public static String encryptToBase64(String source,String key){
        String encode = "";
        encode = DesUtils.encodeBASE64(source);
        encode = DesUtils.enOrDe(encode, key);
        try {
        	encode = URLEncoder.encode(encode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encode;
    }
    
    
    
    /***
     * （加密/解密）的字符串（异或算法）
     * @param source 需要（加密/解密）的字符串（必填）
     * @param key 加密key（必填）
     * @return （加密/解密）后的字符串
     */
    public static String enOrDe(String source,String key){
        String decrypt_str = "";
        byte[] byte_decs = null;
        byte[] byte_key_decs = null;
        if(StringUtils.isEmpty(source)||StringUtils.isEmpty(key)){
            // 需要（加密/解密）的字符串或加密key为空
            return null;
        }
        byte_decs = source.getBytes();
        byte_key_decs = key.getBytes();
        
        for(int i=0;i<byte_decs.length;i++){
            for(byte b:byte_key_decs){
                byte_decs[i] =(byte)(byte_decs[i]^b);
            }
        }
        decrypt_str = new String(byte_decs);
        //
        return decrypt_str;
    }
    
    
    /***
     * BASE64 加密
     * @param source
     * @return String
     */
    public static String encodeBASE64(String source){
        return new BASE64Encoder().encode(source.getBytes());
    }
    
    /***
     * BASE64 解密
     * @param source
     * @return String
     */
    public static String decodeBASE64(String source){
        String r_str = null;
        try {
            r_str = new String(new BASE64Decoder().decodeBuffer(source));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return r_str;
    }
    /***
     * 主方法
     * @param args
     */
    public static void main(String[] args) {
    	String source = "cb6d526ff7b84747=111111";
        String key = "f3eb4c02-c0d6-42e1-ad15-13610c6e88a9";
        System.out.println("key："+key);
      
        String dec = "USUzQUElM0FSTCU1RHFGZVJlRiUzQUElM0NGTGs4RnI4cEUlNUNNcEUlNUNNNQ==";

        System.out.println("source："+source);
        source = DesUtils.encryptToBase64(source, key);
        System.out.println("source："+source);

        //dec = new String(Base64.decodeBase64(dec));
        source = DesUtils.decryptByBase64(source,key);
        
        System.out.println("source："+source);
//        
//        source = DesUtils.encodeBASE64(source);
//        System.out.println("BASE64加密："+source);
//        
//        String str = DesUtils.enOrDe(source, key);
//        System.out.println("异或加密："+str);
//        str = DesUtils.enOrDe(str, key);
//        System.out.println("异或解密："+str);
//        
//        str = DesUtils.decodeBASE64(str);
//        System.out.println("BASE64解密："+str);
        
    }
}
