package com.pase.transport.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateOrderRequest(
		
		 @NotBlank(message = "Origin is required")
		 @Schema(example = "Ciudad de México")
	     String origin,

	     @NotBlank(message = "Destination is required")
		 @Schema(example = "Guadalajara")
	     String destination
	     ) {

}
