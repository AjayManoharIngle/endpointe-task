package org.endpointe.service;

import org.endpointe.model.UserDto;
import org.springframework.data.domain.Page;

public interface UserService {

	UserDto addUser(UserDto e) throws Exception ;

	UserDto updateUser(UserDto UserDto, Long UserId) throws Exception;

	void deleteUser(Long UserId) throws Exception;

	UserDto getUser(Long UserId) throws Exception;
	
	Page<UserDto> getAllUser(int page, int size, String sortBy, String sortDir);
}
