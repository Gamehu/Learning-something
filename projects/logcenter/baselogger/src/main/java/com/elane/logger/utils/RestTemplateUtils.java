package com.elane.logger.utils;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtils {

	private static int connectTimeout = 100000;
	private static int readTimeout = 100000;

	public static String restRequest(String url, Map<String, String> parameter, final Map<String, String> headers){
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(connectTimeout);
		factory.setReadTimeout(readTimeout);
		
		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		if(parameter != null){
			for(Entry<String, String> str : parameter.entrySet()){
				form.add(str.getKey(), str.getValue());
			}
		}
		
		RestTemplate restTemplate;
		//如果需要request特殊处理，例如header添加信息，匿名内部类实现createRequest的重写
		restTemplate = new RestTemplate(factory) {
			@Override
			protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException {
				ClientHttpRequest httpRequest = super.createRequest(url, HttpMethod.POST);
				if(headers != null){
					for(Entry<String, String> str : headers.entrySet()){
						httpRequest.getHeaders().add(str.getKey(), str.getValue());
					}
				}
				return httpRequest;
			}
		};
		
		String jsonString = "";
		jsonString = restTemplate.postForObject(url, form, String.class);
		return jsonString;
	}
	
	public static void main(String[] args) {
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("code", "123");
		Map<String, String> headers = new HashMap<String, String>();
		parameter.put("head", "456");
		restRequest("http://192.168.1.64:8087/baselogger/oauth/userlist", parameter, headers);
	}

}
