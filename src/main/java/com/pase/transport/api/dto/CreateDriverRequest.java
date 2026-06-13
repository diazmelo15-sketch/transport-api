package com.pase.transport.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateDriverRequest(
		@NotBlank(message = "name is required") 
		String name,

		@NotBlank (message = "licenseNumber is required") 
		String licenseNumber) {

}
