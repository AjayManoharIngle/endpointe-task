package org.endpointe.service;

import org.endpointe.entity.UserMaster;
import org.endpointe.exception.UserManagementException;
import org.endpointe.model.AuthRequest;
import org.endpointe.model.AuthResponse;

import jakarta.security.auth.message.AuthException;

public interface AuthService {

	AuthResponse loginUser(AuthRequest authRequest) throws UserManagementException ;

	UserMaster validateAuthorizedToken(String authorization) throws NumberFormatException, UserManagementException, AuthException;

	AuthResponse getRefreshToken(String accessToken, UserMaster employee);
}