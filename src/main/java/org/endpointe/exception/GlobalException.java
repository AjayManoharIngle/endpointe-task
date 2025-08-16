package org.endpointe.exception;

import java.time.Instant;
import java.time.LocalDateTime;

import org.endpointe.exception.entity.ErrorLog;
import org.endpointe.exception.model.ErrorResponse;
import org.endpointe.exception.repository.ErrorLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.ServletWebRequest;

import jakarta.security.auth.message.AuthException;

@RestControllerAdvice
public class GlobalException {
	
	@Autowired
	private ErrorLogRepository errorLogRepository;

	@ExceptionHandler({UserManagementException.class,UserAuthException.class,AuthException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(Exception ex, ServletWebRequest request) {
		HttpStatus status = getStatusAsPerException(ex);
		ErrorResponse error = new ErrorResponse(ex.getMessage(), status.value(),LocalDateTime.now());
        saveErrorLog(request,ex.getMessage(),status.value());
        return new ResponseEntity<>(error, status);
    }

	@ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, ServletWebRequest request) {
        ErrorResponse error = new ErrorResponse("Internal Server Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),LocalDateTime.now());
        saveErrorLog(request,ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	private void saveErrorLog(ServletWebRequest request,String ex,int status) {
		ErrorLog log = new ErrorLog();
		log.setCreatedOn(Instant.now());
        log.setPath(request.getRequest().getRequestURI());
        log.setErrorMessage(ex);
        log.setStatus(status);
        errorLogRepository.save(log);
	}  
	
	private HttpStatus getStatusAsPerException(Exception ex) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	    if (ex instanceof UserManagementException) {
	        status = HttpStatus.BAD_REQUEST;  
	    } else if (ex instanceof UserAuthException) {
	        status = HttpStatus.UNAUTHORIZED; 
	    } else if (ex instanceof AuthException) {
	        status = HttpStatus.FORBIDDEN; 
	    }
	    return status;
	}
}
