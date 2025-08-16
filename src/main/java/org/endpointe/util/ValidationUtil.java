package org.endpointe.util;

import java.util.Optional;

import org.endpointe.entity.UserMaster;
import org.endpointe.exception.UserManagementException;
import org.endpointe.model.UserInfo;
import org.endpointe.repository.UserRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ValidationUtil {
	
    private static final Logger logger = LoggerFactory.getLogger(ValidationUtil.class);
    
    @Autowired
	private UserRespository employeeRepository;

	public void validation(BindingResult bindingResult) throws Exception {
		  if (bindingResult.hasErrors()) {
	            String errorMsg = bindingResult.getFieldErrors().stream()
	                .map(error -> error.getField() + ": " + error.getDefaultMessage())
	                .reduce((a, b) -> a + ", " + b)
	                .orElse("Invalid input");
	            logger.info(errorMsg);
	            throw new UserManagementException(errorMsg);
	      }
	}
	
	public UserMaster validateUser(String userName) throws UserManagementException {
		Optional<UserMaster> customer = employeeRepository.findByEmail(userName);
		if(!customer.isPresent()) {
			throw new UserManagementException("Invalid User email");
		}
		return customer.get();
	}
}
