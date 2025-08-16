package org.endpointe.repository;

import java.util.Optional;

import org.endpointe.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<UserMaster, Long>{

	Optional<UserMaster> findByEmail(String email);
	//Employee findByEmail(String email);
}
