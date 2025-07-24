package org.endpointe.employee.service;

import org.endpointe.employee.model.EmployeeDto;
import org.springframework.data.domain.Page;

public interface EmployeeService {

	EmployeeDto addEmployee(EmployeeDto e) throws Exception ;

	EmployeeDto updateEmployee(EmployeeDto employeeDto, Long employeeId) throws Exception;

	void deleteEmployee(Long employeeId) throws Exception;

	EmployeeDto getEmployee(Long employeeId) throws Exception;
	
	Page<EmployeeDto> getAllEmployee(int page, int size, String sortBy, String sortDir);
}
