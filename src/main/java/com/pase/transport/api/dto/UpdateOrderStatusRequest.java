package com.pase.transport.api.dto;

import com.pase.transport.api.enums.OrderStatus;

import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequest(
		@NotNull OrderStatus status
		) {

}
