package org.endpointe.mapper;

import org.endpointe.entity.UserMaster;
import org.endpointe.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserMaster toEntity(UserDto dto) {
		UserMaster emp = new UserMaster();
		emp.setDepartmentName(dto.getDepartmentName());
		emp.setEmail(dto.getEmail());
		emp.setJoiningDate(dto.getJoiningDate());
		emp.setName(dto.getName());
		emp.setSalary(dto.getSalary());
		emp.setPassword(passwordEncoder.encode(dto.getPassword())); 
		return emp;
	}
	
	public UserDto toDto(UserMaster entity) {
		UserDto dto = new UserDto();
		dto.setDepartmentName(entity.getDepartmentName());
		dto.setEmail(entity.getEmail());
		dto.setId(entity.getId());
		dto.setJoiningDate(entity.getJoiningDate());
		dto.setName(entity.getName());
		dto.setSalary(entity.getSalary());
		return dto;
	}
	
	public Page<UserDto> toDtos(Page<UserMaster> pages) {
		return pages.map(entity -> {
			UserDto dto = new UserDto();
			dto.setDepartmentName(entity.getDepartmentName());
			dto.setEmail(entity.getEmail());
			dto.setId(entity.getId());
			dto.setJoiningDate(entity.getJoiningDate());
			dto.setName(entity.getName());
			dto.setSalary(entity.getSalary());
			return dto;
		});
	}

	public UserMaster toEntity(UserMaster emp, UserDto dto) {
		emp.setDepartmentName(dto.getDepartmentName());
		emp.setEmail(dto.getEmail());
		emp.setJoiningDate(dto.getJoiningDate());
		emp.setName(dto.getName());
		emp.setSalary(dto.getSalary());
		return emp;
	}
	
}
