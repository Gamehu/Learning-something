package org.elane.filter;

import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import com.nimbusds.jose.util.Base64;
import com.nimbusds.jose.util.JSONObjectUtils;

/**
 * client-access-token管理
 * @author zoro
 */
public class ClientAccessTokenManger{

	private ClientConfig clientConfig;
	
	private int defaultExpireTime = 300000; // 5 minutes in milliseconds
	private SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
	private String token;
	private Date cacheExpire;

	private void cacheToken(String token) {
		this.token = token;
		//	缓存token和有效期
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MILLISECOND, defaultExpireTime);
		this.cacheExpire = cal.getTime();
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public ClientConfig getClientConfig() {
		return clientConfig;
	}

	public void setClientConfig(ClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	public int getDefaultExpireTime() {
		return defaultExpireTime;
	}

	public void setDefaultExpireTime(int defaultExpireTime) {
		this.defaultExpireTime = defaultExpireTime;
	}

	public String readAccessToken() {
		checkCache();
		
		if (token != null) {
			return token;
		} else {
			remoteGetToken();
			
			if (token != null) {
				return token;
			} else {
				return null;
			}
		}
	}
	
	/**
	 * --------只缓存有效token、超时的token会被移除---------
	 * @param key is the token to check
	 * @return the cached TokenCacheObject or null
	 */
	private String checkCache() {
		if (this.token != null) {

			if (this.cacheExpire != null && this.cacheExpire.after(new Date())) {
				return token;
			} else {
				//移除无效token
				this.token = null;
			}
		}
		return null;
	}
	
	/**
	 * 获取Access-token
	 */
	private void remoteGetToken(){
		factory.setConnectTimeout(10000);
		factory.setReadTimeout(10000);
		
		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("grant_type", "client_credentials");
		form.add("scope", "clientapi");
		
		RestTemplate restTemplate;
		//如果需要request特殊处理，例如header添加信息，匿名内部类实现createRequest的重写
		restTemplate = new RestTemplate(factory) {
			@Override
			protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException {
				ClientHttpRequest httpRequest = super.createRequest(url, method);
				httpRequest.getHeaders().add("Authorization",
						String.format("Basic %s", Base64.encode(String.format("%s:%s",
								UriUtils.encodePathSegment(clientConfig.getClientId(), "UTF-8"),
								UriUtils.encodePathSegment(clientConfig.getClientSecret(), "UTF-8")))));
				return httpRequest;
			}
		};
		
		String jsonString = "";
		try {
			jsonString = restTemplate.postForObject(clientConfig.getEndPoint(), form, String.class);
			String access_token = JSONObjectUtils.getString(JSONObjectUtils.parseJSONObject(jsonString), "access_token");
			cacheToken(access_token);
		} catch (Exception e) {
			jsonString = e.getMessage();
		}
	}

}
