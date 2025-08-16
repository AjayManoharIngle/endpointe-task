package org.endpointe.config;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
public class JwtConfig {
	private static final Logger logger = LoggerFactory.getLogger(JwtConfig.class);
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration_ms}")
	public long expirationInMS;
	
	@Value("${refresh.token.expiration_ms}")
	public long refreshTokenExpirationMs;
	
	public SecretKey getSigningKey() {
	    byte[] keyBytes = Base64.getDecoder().decode(secret);
	    return Keys.hmacShaKeyFor(keyBytes);
	}
}