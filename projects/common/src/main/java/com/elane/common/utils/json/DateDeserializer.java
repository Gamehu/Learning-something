package com.elane.common.utils.json;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 *
 * @description Created with antnest-platform
 *
 * @author carl
 *
 * @version 创建时间：2016年12月26日 下午2:51:39
 * 
 **/
public class DateDeserializer implements JsonDeserializer<java.util.Date>{

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new java.util.Date(json.getAsJsonPrimitive().getAsLong());
    }

}
