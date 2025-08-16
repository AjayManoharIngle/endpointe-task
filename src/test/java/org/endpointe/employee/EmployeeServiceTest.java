package org.endpointe.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.endpointe.entity.UserMaster;
import org.endpointe.exception.UserManagementException;
import org.endpointe.mapper.UserMapper;
import org.endpointe.model.UserDto;
import org.endpointe.repository.UserRespository;
import org.endpointe.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock
	private UserRespository userRespository;
	
	@Mock
	private UserMapper userMapper;

	@Test
    void testGetEmployeeName_WhenEmployeeExists() throws Exception {
		UserMaster emp = new UserMaster(1L, "Ajay","ajayingle@gmail.com","Developer",50000D,LocalDate.now(),"");
        when(userRespository.findById(1L))
        .thenReturn(Optional.of(emp));

        UserDto empDto = new UserDto(1L, "Ajay", "ajayingle@gmail.com", "Developer", 50000D, LocalDate.now(),"");
        when(userMapper.toDto(emp)).thenReturn(empDto); 

        UserDto name = userService.getUser(1L);
        assertNotNull(name);
        assertEquals("Ajay", name.getName());
    }
	
	@Test
	void testAddEmployee_EmailAlreadyExists_ShouldThrowException() {
	    UserDto dto = new UserDto(null, "Ajay", "ajay@gmail.com", "Developer", 50000D, LocalDate.now(),"");
	    UserMaster empEntity = new UserMaster(1L, "Ajay", "ajay@gmail.com", "Developer", 50000D, LocalDate.now(),"");

	    when(userRespository.findByEmail("ajay@gmail.com")).thenReturn(Optional.of(empEntity));

	    Exception exception = assertThrows(UserManagementException.class, () -> {	
	    	userService.addUser(dto);
	    });

	    assertEquals("Email already exists", exception.getMessage());
	}
	
}
