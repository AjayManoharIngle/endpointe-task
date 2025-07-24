package org.endpointe.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.endpointe.employee.entity.Employee;
import org.endpointe.employee.exception.EmployeeException;
import org.endpointe.employee.mapper.EmployeeMapper;
import org.endpointe.employee.model.EmployeeDto;
import org.endpointe.employee.repository.EmployeeRespository;
import org.endpointe.employee.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
	
	@InjectMocks
	private EmployeeServiceImpl employeeService;
	
	@Mock
	private EmployeeRespository employeeRespository;
	
	@Mock
	private EmployeeMapper employeeMapper;

	@Test
    void testGetEmployeeName_WhenEmployeeExists() throws Exception {
		Employee emp = new Employee(1L, "Ajay","ajayingle@gmail.com","Developer",50000D,LocalDate.now());
        when(employeeRespository.findById(1L))
        .thenReturn(Optional.of(emp));

        EmployeeDto empDto = new EmployeeDto(1L, "Ajay", "ajayingle@gmail.com", "Developer", 50000D, LocalDate.now());
        when(employeeMapper.toDto(emp)).thenReturn(empDto); 

        EmployeeDto name = employeeService.getEmployee(1L);
        assertNotNull(name);
        assertEquals("Ajay", name.getName());
    }
	
	@Test
	void testAddEmployee_EmailAlreadyExists_ShouldThrowException() {
	    EmployeeDto dto = new EmployeeDto(null, "Ajay", "ajay@gmail.com", "Developer", 50000D, LocalDate.now());
	    Employee empEntity = new Employee(1L, "Ajay", "ajay@gmail.com", "Developer", 50000D, LocalDate.now());

	    when(employeeRespository.findByEmail("ajay@gmail.com")).thenReturn(Optional.of(empEntity));

	    Exception exception = assertThrows(EmployeeException.class, () -> {	
	        employeeService.addEmployee(dto);
	    });

	    assertEquals("Email already exists", exception.getMessage());
	}
	
}
