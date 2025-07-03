package com.r2l.authService.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.Date;

@Component
public class JwtUtil {

	private final PrivateKey privateKey;

	public JwtUtil(@Value("classpath:private_key.pem") Resource privateKeyResource) throws Exception {
		try (InputStream inputStream = privateKeyResource.getInputStream()) {
			String key = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
			this.privateKey = PemUtils.parsePrivateKey(key);
		}
	}

	public String generateToken(UserDetails userDetails) {
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(privateKey, SignatureAlgorithm.RS256)
				.compact();
	}

}
