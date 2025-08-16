package org.endpointe.repository;

import java.util.Optional;

import org.endpointe.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRespository extends JpaRepository<RefreshToken, Long>{

	Optional<RefreshToken> findByUserId(Long id);

	RefreshToken findByTokenIdAndUserIdAndRevokedFalse(String token, Long userId);
}
