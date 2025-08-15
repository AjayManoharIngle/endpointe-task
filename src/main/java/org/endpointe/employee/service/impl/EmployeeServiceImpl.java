package org.endpointe.employee.service.impl;

import java.util.Optional;

import org.endpointe.employee.entity.Employee;
import org.endpointe.employee.exception.EmployeeException;
import org.endpointe.employee.mapper.EmployeeMapper;
import org.endpointe.employee.model.EmployeeDto;
import org.endpointe.employee.repository.EmployeeRespository;
import org.endpointe.employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{
	
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private EmployeeRespository employeeRespository;
	
	@Autowired
	private EmployeeMapper employeeMapper;

	@Override
	public EmployeeDto addEmployee(EmployeeDto e) throws Exception {
		Optional<Employee> emp = existsByEmail(e.getEmail());
		if(emp.isPresent()) {
			throw new EmployeeException("Email already exists");
		}
		return employeeMapper.toDto(employeeRespository.save(employeeMapper.toEntity(e)));
	}

	@Override
	public EmployeeDto updateEmployee(EmployeeDto employeeDto, Long employeeId) throws Exception {
		Employee emp = getEmployeeById(employeeId);
		return employeeMapper.toDto(employeeRespository.save(employeeMapper.toEntity(emp,employeeDto)));
	}

	@Override
	public void deleteEmployee(Long employeeId) throws Exception {
		Employee emp = getEmployeeById(employeeId);
		employeeRespository.delete(emp);
		logger.info("delete employee Id : " + employeeId);
	}
	
	@Override
	public EmployeeDto getEmployee(Long employeeId) throws Exception {
		return employeeMapper.toDto(getEmployeeById(employeeId));
	}

	@Override
	public Page<EmployeeDto> getAllEmployee(int page, int size,String sortBy,String sortDir) {
	    Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pages = PageRequest.of(page, size,sort);
		return employeeMapper.toDtos(employeeRespository.findAll(pages));
	}
	
	private Employee getEmployeeById(Long employeeId) throws Exception {
		Employee emp  = employeeRespository.findById(employeeId)
				.orElseThrow(() -> new EmployeeException("Employee is not found with given id : " + employeeId));
		return emp;
	}
							
	private Optional<Employee> existsByEmail(String empEmail) throws EmployeeException {
		return employeeRespository.findByEmail(empEmail);
	}
}
