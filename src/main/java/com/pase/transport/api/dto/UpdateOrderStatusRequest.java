package com.pase.transport.api.dto;

import com.pase.transport.api.util.OrderStatus;

import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequest(
		@NotNull OrderStatus status
		) {

}
