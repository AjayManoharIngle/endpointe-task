package org.endpointe.service.impl;

import java.util.ArrayList;

import org.endpointe.entity.UserMaster;
import org.endpointe.exception.UserAuthException;
import org.endpointe.repository.UserRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UserRespository employeeRepository;
	
    @Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserMaster employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UserAuthException("User not found: " + email));
        
        return new org.springframework.security.core.userdetails.User(
        		employee.getEmail(),
        		employee.getPassword(),
                new ArrayList<>()
        );
	}
}