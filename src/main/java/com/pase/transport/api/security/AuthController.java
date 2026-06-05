package com.pase.transport.api.security;

import com.pase.transport.api.security.dto.AuthenticationRequest;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	 private final AuthenticationManager authenticationManager;
	    private final JwtService jwtService;

	    @PostMapping("/login")
	    public ResponseEntity<?> login(
	            @RequestBody AuthenticationRequest request) {

	        try {

	            authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(
	                            request.username(),
	                            request.password()));

	            String token =
	                    jwtService.generateToken(
	                            request.username());

	            return ResponseEntity.ok(
	                    Map.of("token", token));

	        } catch (BadCredentialsException ex) {

	            return ResponseEntity.status(
	                    HttpStatus.UNAUTHORIZED)
	                    .body(Map.of(
	                            "message",
	                            "Usuario o contraseña incorrectos"));
	        }
	    }
	    
	    @PostMapping("/crear-password")
	    public ResponseEntity<?> password() {
	    	BCryptPasswordEncoder encoder =
	                new BCryptPasswordEncoder();

	        System.out.println(
	                encoder.encode("admin123"));
	       
	        return ResponseEntity.ok("ok");
	    }
}
