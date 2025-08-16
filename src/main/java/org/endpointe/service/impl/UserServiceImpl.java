package org.endpointe.service.impl;

import java.util.Optional;

import org.endpointe.entity.UserMaster;
import org.endpointe.exception.UserManagementException;
import org.endpointe.mapper.UserMapper;
import org.endpointe.model.UserDto;
import org.endpointe.repository.UserRespository;
import org.endpointe.service.UserService;
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
public class UserServiceImpl implements UserService{
	
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRespository userRespository;
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDto addUser(UserDto userDto) throws Exception {
		Optional<UserMaster> userMaster = existsByEmail(userDto.getEmail());
		if(userMaster.isPresent()) {
			throw new UserManagementException("Email already exists");
		}
		return userMapper.toDto(userRespository.save(userMapper.toEntity(userDto)));
	}

	@Override
	public UserDto updateUser(UserDto UserDto, Long UserId) throws Exception {
		UserMaster userMaster = getUserById(UserId);
		return userMapper.toDto(userRespository.save(userMapper.toEntity(userMaster,UserDto)));
	}

	@Override
	public void deleteUser(Long UserId) throws Exception {
		UserMaster emp = getUserById(UserId);
		userRespository.delete(emp);
		logger.info("delete User Id : " + UserId);
	}
	
	@Override
	public UserDto getUser(Long UserId) throws Exception {
		return userMapper.toDto(getUserById(UserId));
	}

	@Override
	public Page<UserDto> getAllUser(int page, int size,String sortBy,String sortDir) {
	    Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pages = PageRequest.of(page, size,sort);
		return userMapper.toDtos(userRespository.findAll(pages));
	}
	
	private UserMaster getUserById(Long UserId) throws Exception {
		UserMaster userMaster  = userRespository.findById(UserId)
				.orElseThrow(() -> new UserManagementException("User is not found with given id : " + UserId));
		return userMaster;
	}
							
	private Optional<UserMaster> existsByEmail(String email) throws UserManagementException {
		return userRespository.findByEmail(email);
	}
}
