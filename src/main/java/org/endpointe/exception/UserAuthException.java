package org.endpointe.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserAuthException extends UsernameNotFoundException{

	public UserAuthException(String s) {
		super(s);
	}
}
