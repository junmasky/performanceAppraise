package com.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.util.properties.PropertiesUtils;


public class JWTUtils {
	public String createJWT(String id, String subject, long ttlMillis, Map<String, Object> claims) throws Exception{
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		SecretKey key = generalKey();
		JwtBuilder builder = Jwts.builder()
							.setClaims(claims)
							.setId(id)
							.setIssuedAt(now)
							.setSubject(subject)
							.signWith(signatureAlgorithm, key);
		if(ttlMillis >= 0){
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		return builder.compact();
	}
	
	public Claims parseJWT(String jwt) throws Exception{
		SecretKey key = generalKey();
		Claims claims = Jwts.parser()
						.setSigningKey(key)
						.parseClaimsJws(jwt).getBody();
		return claims;
	}
	
	/**
	 * 由字符串生成加密key
	 * @return
	 */
	public SecretKey generalKey(){
		byte[] encodedKey = Base64.decodeBase64(PropertiesUtils.getProperty("tokenSecret", "sysconfig.properties"));
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}
}
