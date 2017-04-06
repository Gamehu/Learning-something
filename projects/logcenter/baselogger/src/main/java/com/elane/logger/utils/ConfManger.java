package com.elane.logger.utils;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ConfManger {
	
	private final static Logger logger = LoggerFactory.getLogger(ConfManger.class);
	
	private static Properties properties = null;
	
	static {
		FileSystemResourceLoader loader = new FileSystemResourceLoader();
		Resource configResource = loader.getResource("classpath:config.properties");
		try {
			properties = PropertiesLoaderUtils.loadProperties(configResource);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	public static String getValue(String key){
		return (String) properties.get(key);
	}
}
