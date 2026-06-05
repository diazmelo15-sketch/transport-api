package com.pase.transport.api.security;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	 private final JwtAuthenticationEntryPoint authenticationEntryPoint;
	 @Bean
	    SecurityFilterChain securityFilterChain(
	            HttpSecurity http) throws Exception {

	        http
	            .csrf(csrf -> csrf.disable())

	            .exceptionHandling(ex ->
	                    ex.authenticationEntryPoint(
	                            authenticationEntryPoint))

	            .authorizeHttpRequests(auth -> auth
	                    .requestMatchers(
	                            "/api/auth/**",
	                            "/swagger-ui/**",
	                            "/v3/api-docs/**")
	                    .permitAll()
	                    .anyRequest()
	                    .authenticated())

	            .addFilterBefore(
	                    jwtAuthenticationFilter,
	                    UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }
    

    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }
   
}
