package org.endpointe.controller;

import org.endpointe.exception.UserManagementException;
import org.endpointe.model.AuthRequest;
import org.endpointe.model.AuthResponse;
import org.endpointe.service.AuthService;
import org.endpointe.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Tag(name = "Auth Controller", description = "Auth controller endpoints")
@Slf4j
@RequestMapping("/api/v1/users")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
	private AuthService authService;
    
    @Autowired
    private ValidationUtil validationUtil;
    
    @PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest authRequest,BindingResult error) throws Exception{
    	validationUtil.validation(error);
    	return new ResponseEntity<>(authService.loginUser(authRequest),HttpStatus.OK);
	}
    
    @PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshToken(String accessToken,@RequestHeader(name = "authorization",required = true) String authorization) throws UserManagementException, NumberFormatException, AuthException{
    	return new ResponseEntity<>(authService.getRefreshToken(accessToken,authService.validateAuthorizedToken(authorization)),HttpStatus.OK);
	}
}
