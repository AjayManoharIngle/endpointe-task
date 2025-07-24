package org.endpointe.employee.repository;

import java.util.Optional;

import org.endpointe.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRespository extends JpaRepository<Employee, Long>{

	Optional<Employee> findByEmail(String email);
}
