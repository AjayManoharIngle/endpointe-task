package org.endpointe.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthRequest {
	
	@NotNull(message = "{userName.notNull}")
    private String username;
	
	@NotNull(message = "{password.notNull}")
    private String password;
}