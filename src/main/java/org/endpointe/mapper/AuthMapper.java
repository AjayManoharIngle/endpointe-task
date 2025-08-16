package org.endpointe.mapper;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import org.endpointe.config.JwtConfig;
import org.endpointe.entity.RefreshToken;
import org.endpointe.model.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
	
	@Autowired
	private JwtConfig jwtConfig;
	
	private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

	public RefreshToken toEntity(RefreshToken refreshToken, boolean isExistRefershToken, String tokenId, String tokenSecret, Long userId) {
		if(!isExistRefershToken) {
			refreshToken.setUserId(userId);
			refreshToken.setRevoked(false);
			refreshToken.setCreatedAt(Instant.now());
			refreshToken.setExpiresAt(Instant.now().plus(jwtConfig.getRefreshTokenExpirationMs(), ChronoUnit.MILLIS));
		}
		refreshToken.setTokenId(tokenId);
		refreshToken.setSecretHash(bcrypt.encode(tokenSecret));
		refreshToken.setLastUsedAt(Instant.now());
	    return refreshToken;
	}

	public AuthResponse toDto(String token, String refresh) {
		AuthResponse authResponse = new AuthResponse();
        authResponse.setJwtToken(token);
        authResponse.setRefershToken(refresh);
        authResponse.setJwtExpiry(TimeUnit.MILLISECONDS.toSeconds(jwtConfig.getExpirationInMS()));
        authResponse.setRefresTokenExpiry(TimeUnit.MILLISECONDS.toSeconds(jwtConfig.getRefreshTokenExpirationMs()));
        return authResponse;
	}
}
