package com.pase.transport.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.pase.transport.api.util.OrderStatus;

public record OrderResponse(
			UUID id,
	        OrderStatus status,
	        String origin,
	        String destination,
	        LocalDateTime createdAt,
	        LocalDateTime updatedAt
	        ) {

}
