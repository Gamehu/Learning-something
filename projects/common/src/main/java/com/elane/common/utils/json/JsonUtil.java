package com.elane.common.utils.json;

import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.elane.common.constants.ElaneConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


/**
 * Json工具类
 * 
 */
public class JsonUtil {

    private static final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(java.util.Date.class, new DateSerializer())
            .setDateFormat(DateFormat.LONG)
            .registerTypeAdapter(java.util.Date.class, new DateDeserializer())
            .setDateFormat(DateFormat.LONG)
            .create();
    /**
     * Object -> Json
     * 
     * @param src
     * @return
     */
    public static String toJson(final Object src) {
        return gson.toJson(src);
    }

    /**
     * Json -> T
     * 
     * @param json
     * @param classOfT
     * @return
     */
    public static <T> T fromJson(final String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(final String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    /***
     * getByKey from json
     * 
     * @param json
     * @param key
     * @return JsonElement
     */
    public static JsonElement getByKeyFromJson(final String json, final String key) {
        JsonObject jsonObject = JsonUtil.fromJson(json, JsonObject.class);
        JsonElement je = jsonObject.get(key);
        if (je == null) {
            return null;
        }
        return je;
    }

    /***
     * getStringByKey from json
     * 
     * @param json
     * @param key
     * @return JsonElement
     */
    public static String getStringByKeyFromJson(final String json, final String key) {
        JsonObject jsonObject = JsonUtil.fromJson(json, JsonObject.class);
        JsonElement je = jsonObject.get(key);
        if (je == null) {
            return null;
        }
        int type = getJSONType(je.toString());
        if(type==ElaneConstants.FS_JSON_TYPE_ERROR){
            return null;
        }
        if(type==ElaneConstants.FS_JSON_TYPE_OBJECT||type==ElaneConstants.FS_JSON_TYPE_ARRAY){
            return je.toString();
        }
        if(type==ElaneConstants.FS_JSON_TYPE_STRING||type==ElaneConstants.FS_JSON_TYPE_NUM){
            return je.getAsString();
        }
        
        return je.toString();
    }


    /**
     * 根据传入的JSON字符串获取JSON字符串的类型
     * 
     * @param jsonString
     *            JSON字符串
     * @return Integer
     */
    public static int getJSONType(String jsonString) {
        if (jsonString == null) {
            return ElaneConstants.FS_JSON_TYPE_ERROR;
        }
        jsonString = jsonString.trim();
        if (StringUtils.isEmpty(jsonString)) {
            return ElaneConstants.FS_JSON_TYPE_ERROR;
        }
        final char[] strChar = jsonString.substring(0, 1).toCharArray();
        final char firstChar = strChar[0];
        if (firstChar == '{') {
            return ElaneConstants.FS_JSON_TYPE_OBJECT;
        } else if (firstChar == '[') {
            return ElaneConstants.FS_JSON_TYPE_ARRAY;
        } else if (firstChar == '\'') {
            return ElaneConstants.FS_JSON_TYPE_STRING;
        } else if (firstChar == '"') {
            return ElaneConstants.FS_JSON_TYPE_STRING;
        } else {
            return ElaneConstants.FS_JSON_TYPE_NUM;
        }
    }

    /***
     * json关键字转小写（JSONObject）
     * 
     * @param o1
     * @return
     */
    public static JsonObject transObject(JsonObject o1) {
        JsonObject o2 = new JsonObject();
        Set<Entry<String, JsonElement>> it = o1.entrySet();
        for(Entry<String, JsonElement> type : it){
            String key = (String) type.getKey();
            Object object = o1.get(key);
            if (object.getClass().toString().endsWith("JsonObject")) {
                o2.add(key.toLowerCase(), JsonUtil.transObject((JsonObject) object));
            } else if (object.getClass().toString().endsWith("JsonArray") && object.toString().endsWith("}]")) {
                o2.add(key.toLowerCase(), JsonUtil.transArray(o1.getAsJsonArray(key)));
            } else {
                o2.add(key.toLowerCase(), (JsonElement)object);
            }
        }
        return o2;
    }

    /***
     * json关键字转小写（JsonArray）
     * 
     * @param o1
     * @return
     */
    public static JsonArray transArray(JsonArray o1) {
        JsonArray o2 = new JsonArray();
        for (int i = 0; i < o1.size(); i++) {
            Object obj = o1.get(i);
            if (obj.getClass().toString().endsWith("JsonObject")) {
                o2.add(JsonUtil.transObject((JsonObject) obj));
            } else if (obj.getClass().toString().endsWith("JsonArray")) {
                o2.add(JsonUtil.transArray((JsonArray) obj));
            }
        }
        return o2;
    }

    /**
     * 转utf-8编码,将json格式转为小写
     * 
     * @param jsonString
     *            json字符串
     * @return
     * @throws Exception
     */
    public static String formatJson(String jsonString) throws Exception {
        // 将提交参数进行解密
        if (jsonString.indexOf("{") != 0 && jsonString.indexOf("%") == 0) {
            jsonString = URLDecoder.decode(jsonString, ElaneConstants.FS_UTF_8);
        }
        // 验证JSON格式
        if (JsonUtil.getJSONType(jsonString) == ElaneConstants.FS_JSON_TYPE_OBJECT) {
            // 参数统一转成小写
            String jsonStringLower = JsonUtil.transObject(JsonUtil.fromJson(jsonString, JsonObject.class)).toString();
            if (jsonString.length() == jsonStringLower.length()) {
                return jsonStringLower;
            }
        }
        return jsonString;
    }
}
