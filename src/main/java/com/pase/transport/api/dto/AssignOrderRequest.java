package com.pase.transport.api.dto;

import java.util.UUID;

public record AssignOrderRequest(
		   UUID orderId,
	       UUID driverId
	       ) {

}
