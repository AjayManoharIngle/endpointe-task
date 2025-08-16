package org.endpointe.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.endpointe.config.JwtConfig;
import org.endpointe.entity.UserMaster;
import org.endpointe.exception.UserAuthException;
import org.endpointe.exception.UserManagementException;
import org.endpointe.mapper.AuthMapper;
import org.endpointe.model.AuthRequest;
import org.endpointe.model.AuthResponse;
import org.endpointe.model.UserInfo;
import org.endpointe.repository.UserRespository;
import org.endpointe.service.AuthService;
import org.endpointe.util.JwtUtil;
import org.endpointe.util.RefreshTokenUtil;
import org.endpointe.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import jakarta.security.auth.message.AuthException;

@Service
public class AuthServiceImpl implements AuthService{
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private JwtConfig jwtConfig;
	
	@Autowired
	private ValidationUtil validationUtil;
	
	@Autowired
	private UserRespository employeeRespository;
	
	@Autowired
	private RefreshTokenUtil refreshTokenUtil;
	
	@Autowired
	private AuthMapper authMapper;
	
	@Override
	public AuthResponse loginUser(AuthRequest authRequest) throws UserManagementException {
		Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
	        );
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		UserMaster employee = employeeRespository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserAuthException("User not found: " + userDetails.getUsername()));
		
        String token = issueJwtToken(userDetails);
        String refresh = refreshTokenUtil.issueRefreshToken(employee.getId());
		logger.info("Entry into loginCustomer token : "+token);
		
		return authMapper.toDto(token,refresh);
	}
	
	private String issueJwtToken(UserDetails userDetails) throws InvalidKeyException, UserManagementException {
		return Jwts.builder()
        		.setClaims(getClaims(userDetails))
        		.setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                //.setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpirationInMS()))
                .signWith(jwtConfig.getSigningKey(),SignatureAlgorithm.HS256)
                .compact();
	}

	private Map<String, Object> getClaims(UserDetails userDetails) throws UserManagementException {
		 UserMaster customer= validationUtil.validateUser(userDetails.getUsername());
		 Map<String, Object> claims = new HashMap<>();
		 claims.put("username", customer.getEmail());
		 claims.put("email", customer.getName());
		 claims.put("id",customer.getId());
		 return claims;
	}

	@Override
	public UserMaster validateAuthorizedToken(String authorization) throws NumberFormatException, UserManagementException, AuthException {
		String token = authorization.replace("Bearer ", "");	
		UserInfo username = jwtUtil.extractUserInfo(token);
		return validationUtil.validateUser(username.getUsername());
	}

	@Override
	public AuthResponse getRefreshToken(String refreshToken,UserMaster employee) throws UserAuthException{
		String[] pair = refreshToken.split("\\.");
		if(pair == null || pair.length==0) {
			logger.info("Invalid refresh token : " + refreshToken);
			throw new UserAuthException("Invalid refresh token");
		}
		return authMapper.toDto(null,refreshTokenUtil.reIssueRefreshToken(refreshTokenUtil.validateRefreshToken(pair[0],pair[1],employee.getId())));
	}
}