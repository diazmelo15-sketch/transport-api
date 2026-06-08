package com.pase.transport.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pase.transport.api.dto.CreateOrderRequest;
import com.pase.transport.api.dto.OrderResponse;
import com.pase.transport.api.entity.Order;
import com.pase.transport.api.util.OrderStatus;
import com.pase.transport.api.exception.ResourceNotFoundException;
import com.pase.transport.api.mapper.OrderMapper;
import com.pase.transport.api.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

	 @Mock
	    private OrderRepository orderRepository;

	    @Mock
	    private OrderMapper orderMapper;

	    @InjectMocks
	    private OrderServiceImpl orderService;

	    private UUID orderId;
	    private Order order;

	    @BeforeEach
	    void setup() {

	        orderId = UUID.randomUUID();

	        order = Order.builder()
	                .id(orderId)
	                .status(OrderStatus.CREATED)
	                .origin("Ciudad de México")
	                .destination("Guadalajara")
	                .createdAt(LocalDateTime.now())
	                .updatedAt(LocalDateTime.now())
	                .build();
	    }

	    //Verifica que una orden pueda crearse correctamente.
	    @Test
	    void shouldCreateOrder() {

	        CreateOrderRequest request =
	                new CreateOrderRequest(
	                        "Ciudad de México",
	                        "Guadalajara");

	        OrderResponse response =
	                new OrderResponse(
	                        orderId,
	                        OrderStatus.CREATED,
	                        "Ciudad de México",
	                        "Guadalajara",
	                        LocalDateTime.now(),
	                        LocalDateTime.now());

	        when(orderMapper.toEntity(request))
	                .thenReturn(order);

	        when(orderRepository.save(any(Order.class)))
	                .thenReturn(order);

	        when(orderMapper.toResponse(order))
	                .thenReturn(response);

	        OrderResponse result =
	                orderService.create(request);

	        assertNotNull(result);
	        assertEquals(OrderStatus.CREATED, result.status());

	        verify(orderRepository)
	                .save(any(Order.class));
	    }

	    //Verifica que una orden existente pueda consultarse.
	    @Test
	    void shouldFindOrderById() {

	        OrderResponse response =
	                new OrderResponse(
	                        orderId,
	                        OrderStatus.CREATED,
	                        "Ciudad de México",
	                        "Guadalajara",
	                        LocalDateTime.now(),
	                        LocalDateTime.now());

	        when(orderRepository.findById(orderId))
	                .thenReturn(Optional.of(order));

	        when(orderMapper.toResponse(order))
	                .thenReturn(response);

	        OrderResponse result =
	                orderService.findById(orderId);

	        assertNotNull(result);
	        assertEquals(orderId, result.id());
	    }

	    //Valida el escenario negativo.
	    @Test
	    void shouldThrowExceptionWhenOrderNotFound() {

	        when(orderRepository.findById(orderId))
	                .thenReturn(Optional.empty());

	        assertThrows(
	                ResourceNotFoundException.class,
	                () -> orderService.findById(orderId));
	    }

	    //Verifica la actualización del estado de una orden.
	    @Test
	    void shouldUpdateStatus() {

	        OrderResponse response =
	                new OrderResponse(
	                        orderId,
	                        OrderStatus.IN_TRANSIT,
	                        "Ciudad de México",
	                        "Guadalajara",
	                        LocalDateTime.now(),
	                        LocalDateTime.now());

	        when(orderRepository.findById(orderId))
	                .thenReturn(Optional.of(order));

	        when(orderRepository.save(any(Order.class)))
	                .thenReturn(order);

	        when(orderMapper.toResponse(any(Order.class)))
	                .thenReturn(response);

	        OrderResponse result =
	                orderService.updateStatus(
	                        orderId,
	                        OrderStatus.IN_TRANSIT);

	        assertEquals(
	                OrderStatus.IN_TRANSIT,
	                result.status());

	        verify(orderRepository)
	                .save(any(Order.class));
	    }
}
