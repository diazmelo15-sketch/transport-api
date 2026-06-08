package com.pase.transport.api.security;

import com.pase.transport.api.security.dto.AuthenticationRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Api para logeo")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	@PostMapping("/login")
	@Operation(summary = "Genera token")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {

		try {

			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

			String token = jwtService.generateToken(request.username());

			return ResponseEntity.ok(Map.of("token", token));

		} catch (BadCredentialsException ex) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("message", "Usuario o contraseña incorrectos"));
		}
	}

}
