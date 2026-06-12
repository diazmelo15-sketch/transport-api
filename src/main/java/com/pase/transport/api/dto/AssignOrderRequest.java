package com.pase.transport.api.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record AssignOrderRequest(
		 	@NotNull(message = "orderId is required")
		    UUID orderId,

		    @NotNull(message = "driverId is required")
		    UUID driverId
	       ) {

}
