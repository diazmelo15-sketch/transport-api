package com.pase.transport.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pase.transport.api.dto.CreateOrderRequest;
import com.pase.transport.api.dto.OrderResponse;
import com.pase.transport.api.dto.UpdateOrderStatusRequest;
import com.pase.transport.api.enums.OrderStatus;
import com.pase.transport.api.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Gestión de órdenes de transporte")
public class OrderController {

	
	   private final OrderService orderService;
	   
	   @PostMapping
	   @Operation(summary = "Crear una nueva orden")
	    public ResponseEntity<OrderResponse> create(
	            @Valid
	            @RequestBody CreateOrderRequest request) {

	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(orderService.create(request));
	    }

	    @GetMapping("/{id}")
	    @Operation(summary = "Consultar orden por ID")
	    public ResponseEntity<OrderResponse> findById(
	            @PathVariable UUID id) {

	        return ResponseEntity.ok(
	                orderService.findById(id));
	    }

	    @PatchMapping("/{id}/status")
	    @Operation(summary = "Cambiar status de orden por ID")
	    public ResponseEntity<OrderResponse> updateStatus(
	            @PathVariable UUID id,
	            @RequestBody UpdateOrderStatusRequest request) {

	        return ResponseEntity.ok(
	                orderService.updateStatus(
	                        id,
	                        request.status()));
	    }

	    @GetMapping
	    @Operation(summary = "Consultar ordenes por filtros")
	    public ResponseEntity<List<OrderResponse>> findAll(
	            @RequestParam(required = false)
	            OrderStatus status,

	            @RequestParam(required = false)
	            String origin,

	            @RequestParam(required = false)
	            String destination) {

	        return ResponseEntity.ok(
	                orderService.findAll(
	                        status,
	                        origin,
	                        destination));
	    }
	    
}
