package org.endpointe.filter;

import java.io.IOException;

import org.endpointe.model.UserInfo;
import org.endpointe.service.impl.CustomUserDetailsService;
import org.endpointe.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
		logger.info("Entry into doFilterInternal()");

        final String authHeader = request.getHeader("Authorization");
        logger.info("Entry into doFilterInternal() authHeader : "+authHeader);
        
        String jwt = null;
        UserInfo userInfo = null;
        try {
	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            jwt = authHeader.substring(7);
				userInfo = jwtUtil.extractUserInfo(jwt);
		        logger.info("Entry into doFilterInternal() username : "+userInfo.getUsername());
	        }
	        if (userInfo != null && userInfo.getUsername() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails userDetails = userDetailsService.loadUserByUsername(userInfo.getUsername());
	            logger.info("Entry into doFilterInternal() username : "+userDetails);
	            if (jwtUtil.validateToken(jwt, userDetails)) {
				    logger.info("Entry into doFilterInternal() jwt : "+jwt);
				    UsernamePasswordAuthenticationToken authToken =
				            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	
				    SecurityContextHolder.getContext().setAuthentication(authToken);
				}	
	        }
        }catch (AuthException e) {
            logger.error("Authentication error: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}