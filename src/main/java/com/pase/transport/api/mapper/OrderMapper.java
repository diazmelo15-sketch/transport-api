package com.pase.transport.api.mapper;

import org.mapstruct.Mapper;

import com.pase.transport.api.dto.CreateOrderRequest;
import com.pase.transport.api.dto.OrderResponse;
import com.pase.transport.api.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	OrderResponse toResponse(Order order);

    Order toEntity(CreateOrderRequest request);
}
