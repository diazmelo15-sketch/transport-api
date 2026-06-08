package com.pase.transport.api.mapper;

import org.mapstruct.Mapper;

import com.pase.transport.api.dto.CreateDriverRequest;
import com.pase.transport.api.dto.DriverResponse;
import com.pase.transport.api.entity.Driver;

@Mapper(componentModel = "spring")
public interface DriverMapper {

	DriverResponse toResponse(Driver driver);

    Driver toEntity(CreateDriverRequest request);
    
}
