package org.endpointe.util;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

import org.endpointe.entity.RefreshToken;
import org.endpointe.exception.UserAuthException;
import org.endpointe.mapper.AuthMapper;
import org.endpointe.repository.RefreshTokenRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenUtil {	
	
	private static final SecureRandom RNG = new SecureRandom();
	private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	
	@Autowired
	private RefreshTokenRespository refreshTokenRepository;
	
	@Autowired
	private AuthMapper authMapper;
	
	private String[] generateTokenPair() {
	    byte[] id = new byte[16];
	    byte[] secret = new byte[32];
	    RNG.nextBytes(id);
	    RNG.nextBytes(secret);
	    return new String[]{
	        Base64.getUrlEncoder().withoutPadding().encodeToString(id),
	        Base64.getUrlEncoder().withoutPadding().encodeToString(secret)
	    };
	}
	
	public String issueRefreshToken(Long userId) {
	    String[] pair = generateTokenPair();
	    String tokenId = pair[0], tokenSecret = pair[1];
	    RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
	    		.map(rt -> {
	    			if(Instant.now().isBefore(rt.getExpiresAt())) {
	    				return authMapper.toEntity(rt,true,tokenId,tokenSecret,userId);
	    			}else {
	    				return authMapper.toEntity(rt,false,tokenId,tokenSecret,userId);
	    			}
	    		})
	    		.orElseGet(()->{
	    		    return authMapper.toEntity(new RefreshToken(), false,tokenId,tokenSecret,userId);
	    		});
	    refreshTokenRepository.save(refreshToken);
	    return tokenId + "." + tokenSecret;
	}
	
	public String reIssueRefreshToken(RefreshToken refreshToken) {
	    String[] pair = generateTokenPair();
	    String tokenId = pair[0], tokenSecret = pair[1];
	    refreshTokenRepository.save(authMapper.toEntity(refreshToken,true,tokenId,tokenSecret,refreshToken.getUserId()));
	    return tokenId + "." + tokenSecret;
	 }
	
	public RefreshToken validateRefreshToken(String token,String tokenHash, Long userId) throws UserAuthException {
		RefreshToken refershToken = refreshTokenRepository.findByTokenIdAndUserIdAndRevokedFalse(token,userId);
		if(refershToken == null) {
			throw new UserAuthException("No refresh token found for user : " + userId);
		}
		if (!bcrypt.matches(tokenHash, refershToken.getSecretHash())) {
            throw new RuntimeException("Invalid refresh token secret");
        } 
		if (refershToken.isRevoked() || Instant.now().isAfter(refershToken.getExpiresAt())) {
            throw new RuntimeException("Expired or revoked");
        }
		return refershToken;
	}
}																																																																																																																																																																																																																																																																																																																													
