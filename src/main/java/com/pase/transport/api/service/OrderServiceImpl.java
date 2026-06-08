package com.pase.transport.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pase.transport.api.dto.CreateOrderRequest;
import com.pase.transport.api.dto.OrderResponse;
import com.pase.transport.api.entity.Order;
import com.pase.transport.api.util.OrderSpecification;
import com.pase.transport.api.util.OrderStatus;
import com.pase.transport.api.util.StatusTransitionValidator;
import com.pase.transport.api.exception.ResourceNotFoundException;
import com.pase.transport.api.mapper.OrderMapper;
import com.pase.transport.api.repository.OrderRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{

	 private final OrderRepository orderRepository;
	    private final OrderMapper orderMapper;

	    @Override
	    public OrderResponse create(CreateOrderRequest request) {

	        log.info(
	                "[CREATE_ORDER] Creando orden origen={} destino={}",
	                request.origin(),
	                request.destination());

	        Order order = orderMapper.toEntity(request);

	        order.setStatus(OrderStatus.CREATED);
	        order.setCreatedAt(LocalDateTime.now());
	        order.setUpdatedAt(LocalDateTime.now());

	        Order saved = orderRepository.save(order);

	        log.info(
	                "[CREATE_ORDER] Orden creada id={} status={}",
	                saved.getId(),
	                saved.getStatus());

	        OrderResponse response = orderMapper.toResponse(saved);
	        return response;
	    }
	    
	    @Override
	    @Transactional(readOnly = true)
	    public OrderResponse findById(UUID id) {

	    	log.info("[FIND_ORDER] Consultando orden id={}", id);

	        Order order = orderRepository.findById(id)
	                .orElseThrow(() -> {
	                    log.warn("[FIND_ORDER] Orden no encontrada id={}", id);
	                    return new ResourceNotFoundException(
	                            "Order not found");
	                });
	        log.info("[FIND_ORDER] Orden encontrada id={}", id);
	        return orderMapper.toResponse(order);
	    }

	    @Override
	    public OrderResponse updateStatus(
	            UUID id,
	            OrderStatus newStatus) {

	    	log.info(
	    	        "[UPDATE_ORDER_STATUS] Actualizando orden id={} nuevoEstado={}",
	    	        id,
	    	        newStatus);

	        Order order = orderRepository.findById(id)
	                .orElseThrow(() -> {
	                    log.warn("[UPDATE_ORDER_STATUS] Orden no encontrada id={}", id);
	                    return new ResourceNotFoundException(
	                            "Order not found");
	                });

	        OrderStatus previousStatus = order.getStatus();

	        StatusTransitionValidator.validateTransition(previousStatus, newStatus);

	        order.setStatus(newStatus);
	        order.setUpdatedAt(LocalDateTime.now());

	        Order saved = orderRepository.save(order);

	        log.info(
	                "[UPDATE_ORDER_STATUS] Estado actualizado orden id={} {} -> {}",
	                id,
	                previousStatus,
	                newStatus);

	        return orderMapper.toResponse(saved);
	    }

	    @Override
	    @Transactional(readOnly = true)
	    public List<OrderResponse> findAll(
	            OrderStatus status,
	            String origin,
	            String destination) {

	        try {

	            log.info(
	                    "[FIND_ALL_ORDERS] Buscando órdenes status={} origin={} destination={}",
	                    status,
	                    origin,
	                    destination);

	            Specification<Order> specification =
	                    OrderSpecification.filter(
	                            status,
	                            origin,
	                            destination);

	            List<OrderResponse> orders =
	                    orderRepository.findAll(specification)
	                            .stream()
	                            .map(orderMapper::toResponse)
	                            .toList();

	            log.info(
	                    "[FIND_ALL_ORDERS] Total de órdenes encontradas={}",
	                    orders.size());

	            return orders;

	        } catch (Exception ex) {

	            log.error(
	                    "[FIND_ALL_ORDERS] Error al consultar órdenes status={} origin={} destination={}",
	                    status,
	                    origin,
	                    destination,
	                    ex);

	            throw ex;
	        }
	    }

		

}
