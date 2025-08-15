package org.endpointe.employee.exception;

import java.time.Instant;
import java.time.LocalDateTime;

import org.endpointe.employee.exception.entity.ErrorLog;
import org.endpointe.employee.exception.repository.ErrorLogRepository;
import org.endpointe.employee.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.ServletWebRequest;

@RestControllerAdvice
public class GlobalException {
	
	@Autowired
	private ErrorLogRepository errorLogRepository;

	@ExceptionHandler(EmployeeException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotFound(EmployeeException ex, ServletWebRequest request) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),LocalDateTime.now());
        saveErrorLog(request,ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, ServletWebRequest request) {
        ErrorResponse error = new ErrorResponse("Internal Server Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),LocalDateTime.now());
        saveErrorLog(request,ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<String> handleClientError(HttpClientErrorException ex, ServletWebRequest request) {
        saveErrorLog(request,ex.getMessage(),ex.getStatusCode().value());
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
    }
	
	private void saveErrorLog(ServletWebRequest request,String ex,int status) {
		ErrorLog log = new ErrorLog();
		log.setCreatedOn(Instant.now());
        log.setPath(request.getRequest().getRequestURI());
        log.setErrorMessage(ex);
        log.setStatus(status);
        errorLogRepository.save(log);
	}  
}
