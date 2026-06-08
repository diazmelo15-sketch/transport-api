package com.pase.transport.api.dto;


import java.util.UUID;


public record DriverResponse(
		UUID id,
        Boolean active,
        String name,
        String licenseNumber) {

}
