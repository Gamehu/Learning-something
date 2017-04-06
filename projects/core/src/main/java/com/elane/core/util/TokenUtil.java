package com.elane.core.util;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.elane.common.constants.ElaneConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.compression.CompressionCodecs;
import io.jsonwebtoken.Claims;

import java.security.Key;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * token生成工具类
 * 
 * */
public class TokenUtil {
	
	/**
	 * 获取token字符串
	 * @param	userid		用户ID
	 * @param	issuer		该JWT的签发者，是否使用是可选的
	 * @param	subject		该JWT所面向的用户，是否使用是可选的
	 * @param   aud         接收该JWT的一方，是否使用是可选的
	 * @param	ttlMillis	token过期时间 
	 * */
	public static String getJWTString(String userid, String issuer, String subject, String aud,long ttlMillis){
		String result=createJWT(userid,
				"http://drybulk.elane.com/auth/",
				"carl@elane.com",
				"drybulk.elane.com",
				ttlMillis,
				ElaneConstants.COOKIE_TOKEN_SECRET,
				SignatureAlgorithm.HS256,null);
		return result;
	}
	
	
	
	/***
	 * 
	 * 创建jwt token （json web token）
	 * @param	userid		用户ID
	 * @param	issuer		该JWT的签发者，是否使用是可选的
	 * @param	subject		该JWT所面向的用户，是否使用是可选的
	 * @param   aud         接收该JWT的一方，是否使用是可选的
	 * @param	ttlMillis	token过期时间
	 * @param	secret		秘钥
	 * @param	signatureAlgorithm	签名算法
	 * @param	customClaims	用户信息
	 * 
	 * **/
	private static String createJWT(String userid, String issuer, String subject, String aud,long ttlMillis, String secret,SignatureAlgorithm signatureAlgorithm,Map<String,String> customClaims) {

		//签名算法
		if(signatureAlgorithm==null)
			signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		//生成签名密钥 
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		
		/*
		 *  key
		 	MacProvider.generateKey(); //or generateKey(SignatureAlgorithm)
			RsaProvider.generateKeyPair(); //or generateKeyPair(sizeInBits)
			EllipticCurveProvider.generateKeyPair(); //or generateKeyPair(SignatureAlgorithm)
		 */

		//添加构成JWT的参数 
		JwtBuilder builder = Jwts.builder()
				.setHeaderParam("type", "JWT")
				.setHeaderParam("alg", "HS256")
                .claim("userid", userid)  
				.setIssuedAt(now)
				.setSubject(subject)
				.setIssuer(issuer)
				.setAudience(aud)
				.signWith(signatureAlgorithm, signingKey);

		//添加Token过期时间  
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		
		
//		if(customClaims!=null&&!customClaims.isEmpty()){
//			for (String key : customClaims.keySet()) {
//
//			    String value = customClaims.get(key);
//			    builder.claim(key, value);
//			    System.out.println("Key = " + key + ", Value = " + value);
//			}
//		}

		//生成JWT 
		return builder.compressWith(CompressionCodecs.DEFLATE).compact();
	}
	
	/**
	 * 解析jwt token串
	 * @param jwt		需要解析的token字符串
	 * @param secret	秘钥
	 * @return		
	 */
	private static Claims parseJWT(String jwt, String secret /*  Required Claims   */) {
		// This line will throw an exception if it is not a signed JWS (as expected)
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret)).parseClaimsJws(jwt)
				.getBody();
		

		return claims;
	}
	
	/**
	 * 解析jwt token串
	 * @param jwt		需要解析的token字符串
	 * @return		
	 */
	public static Claims parseJWTString(String jwt) {
		// This line will throw an exception if it is not a signed JWS (as expected)
		Claims claims = Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary(ElaneConstants.COOKIE_TOKEN_SECRET))
					.parseClaimsJws(jwt)
					.getBody();
		

		return claims;
	}
	
	public static void main(String[] args){
//		HashMap hm=new HashMap();
//		hm.put("pid", "123");
//		hm.put("surname", "Joe");
//		String jwt=TokenUtil.createJWT("userid", "http://elane.com", "tom@elane.com", 1300819380l, "javaSecret",SignatureAlgorithm.HS256,hm);
//		
//		System.out.println(jwt);
//		
//		Claims claims=TokenUtil.parseJWT(jwt,"javaSecret");
//		System.out.println("ID: " + claims.getId());
//		System.out.println("Subject: " + claims.getSubject());
//		System.out.println("Issuer: " + claims.getIssuer());
//		System.out.println("Expiration: " + claims.getExpiration());	
//		System.out.println("surname: " + claims.get("surname", String.class));	
		
	}

}
