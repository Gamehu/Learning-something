package com.elane.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

/**
 * http请求工具类
 * 
 * */
public class HttpRequestUtil {
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        Integer responseCode=-1;//响应返回码
        String result = "";
        BufferedReader in = null;
        HttpURLConnection connection = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Charset", "UTF-8");
            // 建立实际的连接
            connection.connect();
            // 获取响应码
            responseCode=connection.getResponseCode();
            if (responseCode == 200) {
                // 定义 BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            }
        } catch (Exception e) {
            responseCode=-1;
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if(StringUtils.isEmpty(result)){
            result = "{}" ;
        }
        //加入请求的响应码
        JSONObject json=JSONObject.fromObject(result);
        json.put("responseCode", responseCode);
        return json.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        Integer responseCode=-1;//响应返回码
        PrintWriter out = null;
        BufferedReader in = null;
        HttpURLConnection conn = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 获取响应码
            responseCode=conn.getResponseCode();
            if (responseCode == 200) {
                // 定义BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            }
        } catch (Exception e) {
            responseCode=-1;
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        if(StringUtils.isEmpty(result)){
            result = "{}" ;
        }
        //加入请求的响应码
        JSONObject json=JSONObject.fromObject(result);
        json.put("responseCode", responseCode);
        return json.toString();
    }
    /***
     * 类反射组装URL参数   xx=value&xx2=value2
     * @param object 需要组装的对象
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String assembleURLParams(Object object){
        String params="";
        try {
            Class cls = object.getClass();  
            Field[] fields = cls.getDeclaredFields();  
            params = "";
            for(int i=0; i<fields.length; i++){  
                Field f = fields[i];  
                f.setAccessible(true);  
                String value = f.get(object)==null?"":String.valueOf(f.get(object));
                String fildName = f.getName();
                if(i==0){
                    params += fildName + "=" + value;
                }else{
                    params += "&" + fildName + "=" + value;
                }
            }
        } catch (Exception e) {
            //组装URL参数异常
            params="";
        }
        
        return params;
    }
}
