package org.endpointe.employee.mapper;

import org.endpointe.employee.entity.Employee;
import org.endpointe.employee.model.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

	public Employee toEntity(EmployeeDto dto) {
		Employee emp = new Employee();
		emp.setDepartmentName(dto.getDepartmentName());
		emp.setEmail(dto.getEmail());
		emp.setJoiningDate(dto.getJoiningDate());
		emp.setName(dto.getName());
		emp.setSalary(dto.getSalary());
		return emp;
	}
	
	public EmployeeDto toDto(Employee entity) {
		EmployeeDto dto = new EmployeeDto();
		dto.setDepartmentName(entity.getDepartmentName());
		dto.setEmail(entity.getEmail());
		dto.setId(entity.getId());
		dto.setJoiningDate(entity.getJoiningDate());
		dto.setName(entity.getName());
		dto.setSalary(entity.getSalary());
		return dto;
	}
	
	public Page<EmployeeDto> toDtos(Page<Employee> pages) {
		return pages.map(entity -> {
			EmployeeDto dto = new EmployeeDto();
			dto.setDepartmentName(entity.getDepartmentName());
			dto.setEmail(entity.getEmail());
			dto.setId(entity.getId());
			dto.setJoiningDate(entity.getJoiningDate());
			dto.setName(entity.getName());
			dto.setSalary(entity.getSalary());
			return dto;
		});
	}

	public Employee toEntity(Employee emp, EmployeeDto dto) {
		emp.setDepartmentName(dto.getDepartmentName());
		emp.setEmail(dto.getEmail());
		emp.setJoiningDate(dto.getJoiningDate());
		emp.setName(dto.getName());
		emp.setSalary(dto.getSalary());
		return emp;
	}
	
}
