package com.pase.transport.api.controller;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pase.transport.api.dto.CreateDriverRequest;
import com.pase.transport.api.dto.DriverResponse;
import com.pase.transport.api.service.DriverService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
@Tag(name = "Driver", description = "Gestión de conductores de transporte")
public class DriverController {

	private final DriverService service;

	@PostMapping
	@Operation(summary = "Crear un nuevo conductor")
	public ResponseEntity<DriverResponse> create(@Valid @RequestBody CreateDriverRequest driver) {
		return ResponseEntity.ok(service.create(driver));
	}

	
	@GetMapping("/active")
	@Operation(summary = "Consultar conductores activos")
	public ResponseEntity<List<DriverResponse>> getActive() {
		return ResponseEntity.ok(service.getActiveDrivers());
	}
}
