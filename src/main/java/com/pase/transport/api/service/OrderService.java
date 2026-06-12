package com.pase.transport.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.pase.transport.api.dto.CreateOrderRequest;
import com.pase.transport.api.dto.OrderResponse;
import com.pase.transport.api.util.OrderStatus;

public interface OrderService {

	 OrderResponse create(CreateOrderRequest request);

	    OrderResponse findById(UUID id);

	    OrderResponse updateStatus(
	            UUID id,
	            OrderStatus status);

	    List<OrderResponse> findAll(
	            OrderStatus status,
	            String origin,
	            String destination,
	            LocalDate startDate,
	            LocalDate endDate);
}
