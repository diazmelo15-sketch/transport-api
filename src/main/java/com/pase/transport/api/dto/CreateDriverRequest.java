package com.pase.transport.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateDriverRequest(
		@NotBlank String name,

		@NotBlank String licenseNumber,

		Boolean active) {

}
