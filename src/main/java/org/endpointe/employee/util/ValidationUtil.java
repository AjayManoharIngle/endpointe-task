package org.endpointe.employee.util;

import org.endpointe.employee.controller.EmployeeController;
import org.endpointe.employee.exception.EmployeeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ValidationUtil {
	
    private static final Logger logger = LoggerFactory.getLogger(ValidationUtil.class);

	public void validation(BindingResult bindingResult) throws Exception {
		  if (bindingResult.hasErrors()) {
	            String errorMsg = bindingResult.getFieldErrors().stream()
	                .map(error -> error.getField() + ": " + error.getDefaultMessage())
	                .reduce((a, b) -> a + ", " + b)
	                .orElse("Invalid input");
	            logger.info(errorMsg);
	            throw new EmployeeException(errorMsg);
	        }
	}
}
