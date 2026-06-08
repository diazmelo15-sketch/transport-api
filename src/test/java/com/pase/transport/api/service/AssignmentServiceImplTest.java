package com.pase.transport.api.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pase.transport.api.dto.AssignOrderRequest;
import com.pase.transport.api.dto.AssignmentResponse;
import com.pase.transport.api.entity.Assignment;
import com.pase.transport.api.entity.Driver;
import com.pase.transport.api.entity.Order;
import com.pase.transport.api.util.OrderStatus;
import com.pase.transport.api.exception.BusinessException;
import com.pase.transport.api.exception.ResourceNotFoundException;
import com.pase.transport.api.mapper.AssignmentMapper;
import com.pase.transport.api.mapper.DriverMapper;
import com.pase.transport.api.mapper.OrderMapper;
import com.pase.transport.api.repository.AssignmentRepository;
import com.pase.transport.api.repository.DriverRepository;
import com.pase.transport.api.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class AssignmentServiceImplTest {

	
	  @Mock
	    private DriverRepository driverRepository;

	    @Mock
	    private DriverMapper driverMapper;

	    @InjectMocks
	    private DriverServiceImpl driverService;
	    
	    @Mock
	    private OrderRepository orderRepository;

	    @Mock
	    private OrderMapper orderMapper;

	    @InjectMocks
	    private OrderServiceImpl orderService;
	    
	    @Mock
	    private AssignmentRepository assignmentRepository;
	    
	    @Mock
	    private AssignmentMapper assignmentMapper;
	    
	    @InjectMocks
	    private AssignmentServiceImpl assignmentService;
	    
	    //Caso exitoso
	@Test
	void shouldAssignDriverSuccessfully() {

	    UUID orderId = UUID.randomUUID();
	    UUID driverId = UUID.randomUUID();

	    AssignOrderRequest request =
	            new AssignOrderRequest(orderId, driverId);

	    Order order = Order.builder()
	            .id(orderId)
	            .status(OrderStatus.CREATED)
	            .build();

	    Driver driver = Driver.builder()
	            .id(driverId)
	            .active(true)
	            .build();

	    Assignment assignment = Assignment.builder()
	            .id(UUID.randomUUID())
	            .order(order)
	            .driver(driver)
	            .build();

	    AssignmentResponse response =
	            new AssignmentResponse(
	                    assignment.getId(),
	                    order.getId(),
	                    driver.getId(),
	                    driver.getName(),
	                    null,
	                    null,
	                    LocalDateTime.now());

	    when(orderRepository.findById(orderId))
	            .thenReturn(Optional.of(order));

	    when(driverRepository.findById(driverId))
	            .thenReturn(Optional.of(driver));

	    when(assignmentRepository.existsByOrderId(orderId))
	            .thenReturn(false);

	    when(assignmentRepository.save(any()))
	            .thenReturn(assignment);

	    when(assignmentMapper.toResponse(assignment))
	            .thenReturn(response);

	    AssignmentResponse result =
	            assignmentService.assignDriver(request);

	    assertNotNull(result);

	    verify(assignmentRepository)
	            .save(any(Assignment.class));
	}
	
	//Driver inactivo
	@Test
	void shouldThrowBusinessExceptionWhenDriverInactive() {

	    UUID orderId = UUID.randomUUID();
	    UUID driverId = UUID.randomUUID();

	    AssignOrderRequest request =
	            new AssignOrderRequest(orderId, driverId);

	    Order order = Order.builder()
	            .id(orderId)
	            .status(OrderStatus.CREATED)
	            .build();

	    Driver driver = Driver.builder()
	            .id(driverId)
	            .active(false)
	            .build();

	    when(orderRepository.findById(orderId))
	            .thenReturn(Optional.of(order));

	    when(driverRepository.findById(driverId))
	            .thenReturn(Optional.of(driver));

	    assertThrows(
	            BusinessException.class,
	            () -> assignmentService.assignDriver(request));
	}
	
	//Orden ya asignada
	@Test
	void shouldThrowBusinessExceptionWhenOrderAlreadyAssigned() {

	    UUID orderId = UUID.randomUUID();
	    UUID driverId = UUID.randomUUID();

	    AssignOrderRequest request =
	            new AssignOrderRequest(orderId, driverId);

	    Order order = Order.builder()
	            .id(orderId)
	            .status(OrderStatus.CREATED)
	            .build();

	    Driver driver = Driver.builder()
	            .id(driverId)
	            .active(true)
	            .build();

	    when(orderRepository.findById(orderId))
	            .thenReturn(Optional.of(order));

	    when(driverRepository.findById(driverId))
	            .thenReturn(Optional.of(driver));

	    when(assignmentRepository.existsByOrderId(orderId))
	            .thenReturn(true);

	    assertThrows(
	            BusinessException.class,
	            () -> assignmentService.assignDriver(request));
	}
	
	//Orden no encontrada
	@Test
	void shouldThrowResourceNotFoundWhenOrderNotExists() {

	    UUID orderId = UUID.randomUUID();
	    UUID driverId = UUID.randomUUID();

	    AssignOrderRequest request =
	            new AssignOrderRequest(orderId, driverId);

	    when(orderRepository.findById(orderId))
	            .thenReturn(Optional.empty());

	    assertThrows(
	            ResourceNotFoundException.class,
	            () -> assignmentService.assignDriver(request));
	}
}
