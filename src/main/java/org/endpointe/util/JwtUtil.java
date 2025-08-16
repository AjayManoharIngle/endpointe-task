package org.endpointe.util;

import java.util.Date;
import java.util.List;

import org.endpointe.config.JwtConfig;
import org.endpointe.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.security.auth.message.AuthException;

@Component
public class JwtUtil {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	@Autowired
	private JwtConfig jwtConfig;
	
	public UserInfo extractUserInfo(String token) throws AuthException {
		Claims claims = extractAllClaims(token);
		if(claims == null) {
			throw new AuthException("Invalid token");
		}
		UserInfo userInfo = new UserInfo();
		String userName = claims.get("username",String.class);
		if(userName== null || userName.isEmpty()) {
			throw new AuthException("Invalid User Name");
		}	
		userInfo.setUsername(claims.get("username",String.class));
		userInfo.setRoles(claims.get("roles",List.class));
        return userInfo;
    }

    public boolean validateToken(String token, UserDetails userDetails) throws AuthException {
    	Claims claims = extractAllClaims(token);
    	Date expiryTime = claims.getExpiration();
    	Date now = new Date();
    	if(now.after(expiryTime)) {
    		throw new AuthException("JWT token expired at: " + expiryTime + ", current time: " + now);
    	}
    	if(!extractUserInfo(token).getUsername().equals(userDetails.getUsername())) {
    		throw new AuthException("Invalid User Name");
    	}
        return true;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(Keys.hmacShaKeyFor(jwtConfig.getSigningKey().getEncoded()))
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }
}