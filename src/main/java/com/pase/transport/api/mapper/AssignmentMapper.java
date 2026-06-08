package com.pase.transport.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.pase.transport.api.dto.AssignmentResponse;
import com.pase.transport.api.entity.Assignment;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

	@Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "driver.id", target = "driverId")
    @Mapping(source = "driver.name", target = "driverName")
    AssignmentResponse toResponse(Assignment assignment);
}
