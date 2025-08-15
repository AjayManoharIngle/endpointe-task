package org.endpointe.employee.controller;

import org.endpointe.employee.model.EmployeeDto;
import org.endpointe.employee.service.EmployeeService;
import org.endpointe.employee.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Tag(name = "Employee Controller version 1", description = "Employee management endpoints")
@Slf4j
@RequestMapping("/api/v1/employees")
public class EmployeeController {
	
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ValidationUtil validationUtil;

	@PostMapping
	public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employeeDto,BindingResult error) throws Exception{
		validationUtil.validation(error);
		return new ResponseEntity<>(employeeService.addEmployee(employeeDto),HttpStatus.OK);
	}
	
	@PutMapping("/{employeeId}")
	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long employeeId, @RequestBody @Valid EmployeeDto employeeDto,BindingResult error) throws Exception{
		validationUtil.validation(error);
		return new ResponseEntity<>(employeeService.updateEmployee(employeeDto,employeeId),HttpStatus.OK);
	}
	
	@DeleteMapping("/{employeeId}")
	public ResponseEntity<EmployeeDto> deleteEmployee(@PathVariable Long employeeId)  throws Exception{
		employeeService.deleteEmployee(employeeId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/{employeeId}")
	public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long employeeId)  throws Exception{
		return new ResponseEntity<>(employeeService.getEmployee(employeeId),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Page<EmployeeDto>> getAllEmployee(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir)  throws Exception{
		return new ResponseEntity<>(employeeService.getAllEmployee(page,size, sortBy, sortDir),HttpStatus.OK);
	}
}
