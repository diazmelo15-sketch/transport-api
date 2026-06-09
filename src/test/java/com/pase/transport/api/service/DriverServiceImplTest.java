package com.pase.transport.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pase.transport.api.dto.CreateDriverRequest;
import com.pase.transport.api.dto.DriverResponse;
import com.pase.transport.api.entity.Driver;
import com.pase.transport.api.mapper.DriverMapper;
import com.pase.transport.api.repository.DriverRepository;


@ExtendWith(MockitoExtension.class)
public class DriverServiceImplTest {

	 	@Mock
	    private DriverRepository driverRepository;

	    @Mock
	    private DriverMapper driverMapper;

	    @InjectMocks
	    private DriverServiceImpl driverService;
	    
	    
	    @Test
	    void shouldCreateDriverSuccessfully() {

	        CreateDriverRequest request =
	                new CreateDriverRequest(
	                        "Juan Perez",
	                        "LIC12345"
	                );

	        Driver driver = Driver.builder()
	                .id(UUID.randomUUID())
	                .name("Juan Perez")
	                .licenseNumber("LIC12345")
	                .active(true)
	                .build();

	        DriverResponse response =
	                new DriverResponse(
	                        driver.getId(),
	                        true,
	                        driver.getName(),
	                        driver.getLicenseNumber()
	                );

	        when(driverMapper.toEntity(request))
	                .thenReturn(driver);

	        when(driverRepository.save(any(Driver.class)))
	                .thenReturn(driver);

	        when(driverMapper.toResponse(driver))
	                .thenReturn(response);

	        DriverResponse result = driverService.create(request);

	        assertNotNull(result);
	        assertEquals("Juan Perez", result.name());
	        assertTrue(result.active());

	        verify(driverMapper).toEntity(request);
	        verify(driverRepository).save(any(Driver.class));
	        verify(driverMapper).toResponse(driver);
	    }
	    
	    @Test
	    void shouldReturnActiveDrivers() {

	        Driver driver = Driver.builder()
	                .id(UUID.randomUUID())
	                .name("Juan Perez")
	                .licenseNumber("LIC12345")
	                .active(true)
	                .build();

	        DriverResponse response =
	                new DriverResponse(
	                        driver.getId(),
	                        true,
	                        driver.getName(),
	                        driver.getLicenseNumber()
	                );

	        when(driverRepository.findByActiveTrue())
	                .thenReturn(List.of(driver));

	        when(driverMapper.toResponse(driver))
	                .thenReturn(response);

	        List<DriverResponse> result =
	                driverService.getActiveDrivers();

	        assertEquals(1, result.size());

	        assertEquals(
	                "Juan Perez",
	                result.get(0).name()
	        );

	        verify(driverRepository).findByActiveTrue();
	    }
	    
	    @Test
	    void shouldReturnEmptyListWhenNoActiveDrivers() {

	        when(driverRepository.findByActiveTrue())
	                .thenReturn(Collections.emptyList());

	        List<DriverResponse> result =
	                driverService.getActiveDrivers();

	        assertTrue(result.isEmpty());

	        verify(driverRepository).findByActiveTrue();
	    }
}
