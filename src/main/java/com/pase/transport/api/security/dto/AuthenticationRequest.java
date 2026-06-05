package com.pase.transport.api.security.dto;

public record AuthenticationRequest(
		 String username,
	        String password) {

}
