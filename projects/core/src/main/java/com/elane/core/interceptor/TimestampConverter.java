package com.elane.core.interceptor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;


/**
 *
 * @description
 *          页面字符串日期转换为Timestamp
 * @author carl
 *
 * @version 创建时间：2016年8月24日 下午2:31:49
 * 
 **/
public class TimestampConverter implements Converter<String, Timestamp>{
    private final Logger logger = LoggerFactory.getLogger(TimestampConverter.class);
    @Override
    public Timestamp convert(String source) {
        Timestamp t=null;
        long millionSeconds;
        if(StringUtils.isNotEmpty(source)){
            //注意format的格式要与日期String的格式相匹配
            SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
            try {
                millionSeconds=format.parse(source).getTime();
                t=new Timestamp(millionSeconds);
            } catch (Exception e) {
                try {
                    format =  new SimpleDateFormat("yyyy-MM-dd");
                    millionSeconds = format.parse(source).getTime();
                    t=new Timestamp(millionSeconds);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    logger.error("转换字符串为Timestamp日期失败:",e1);
                }
               
            }
            
        }
        return t;
    }

}
