package com.pase.transport.api.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	 @Value("${auth.username}")
	    private String username;
	 
	 @Value("${auth.password}")
	    private String password;
	 
	  @Override
	    public UserDetails loadUserByUsername(String username)
	            throws UsernameNotFoundException {

	        if ("admin".equals(username)) {
	            return User.builder()
	                    .username(username)
	                    .password(password)
	                    .roles("ADMIN")
	                    .build();
	        }

	        throw new UsernameNotFoundException(
	                "Usuario no encontrado");
	    }
}
