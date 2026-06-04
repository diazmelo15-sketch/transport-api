package com.pase.transport.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pase.transport.api.dto.CreateOrderRequest;
import com.pase.transport.api.dto.OrderResponse;
import com.pase.transport.api.entity.Order;
import com.pase.transport.api.enums.OrderStatus;
import com.pase.transport.api.exception.BusinessException;
import com.pase.transport.api.exception.ResourceNotFoundException;
import com.pase.transport.api.mapper.OrderMapper;
import com.pase.transport.api.repository.OrderRepository;
import com.pase.transport.api.specification.OrderSpecification;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{

	 private final OrderRepository orderRepository;
	    private final OrderMapper orderMapper;

	    @Override
	    public OrderResponse create(
	            CreateOrderRequest request) {

	        Order order = orderMapper.toEntity(request);

	        order.setStatus(OrderStatus.CREATED);
	        order.setCreatedAt(LocalDateTime.now());
	        order.setUpdatedAt(LocalDateTime.now());

	        Order saved = orderRepository.save(order);

	        return orderMapper.toResponse(saved);
	    }

	    @Override
	    @Transactional(readOnly = true)
	    public OrderResponse findById(UUID id) {

	        Order order = orderRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException(
	                                "Order not found"));

	        return orderMapper.toResponse(order);
	    }

	    @Override
	    public OrderResponse updateStatus(
	            UUID id,
	            OrderStatus newStatus) {

	        Order order = orderRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException(
	                                "Order not found"));

	        validateTransition(
	                order.getStatus(),
	                newStatus);

	        order.setStatus(newStatus);
	        order.setUpdatedAt(LocalDateTime.now());

	        return orderMapper.toResponse(
	                orderRepository.save(order));
	    }

	    @Override
	    @Transactional(readOnly = true)
	    public List<OrderResponse> findAll(
	            OrderStatus status,
	            String origin,
	            String destination) {

	        Specification<Order> specification =
	                OrderSpecification.filter(
	                        status,
	                        origin,
	                        destination);

	        return orderRepository.findAll(specification)
	                .stream()
	                .map(orderMapper::toResponse)
	                .toList();
	    }

	    private void validateTransition(
	            OrderStatus current,
	            OrderStatus next) {

	        Map<OrderStatus, List<OrderStatus>> transitions =
	                Map.of(
	                        OrderStatus.CREATED,
	                        List.of(
	                                OrderStatus.IN_TRANSIT,
	                                OrderStatus.CANCELLED),

	                        OrderStatus.IN_TRANSIT,
	                        List.of(
	                                OrderStatus.DELIVERED,
	                                OrderStatus.CANCELLED),

	                        OrderStatus.DELIVERED,
	                        List.of(),

	                        OrderStatus.CANCELLED,
	                        List.of()
	                );

	        if (!transitions.get(current)
	                .contains(next)) {

	            throw new BusinessException(
	                    "Invalid status transition from "
	                            + current + " to " + next);
	        }
	    }

}
