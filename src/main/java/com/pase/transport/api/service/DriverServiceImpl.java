package com.pase.transport.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pase.transport.api.dto.CreateDriverRequest;

import com.pase.transport.api.dto.DriverResponse;
import com.pase.transport.api.entity.Driver;
import com.pase.transport.api.mapper.DriverMapper;
import com.pase.transport.api.repository.DriverRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class DriverServiceImpl implements DriverService{

	 private final DriverRepository driverRepository;
	    private final DriverMapper driverMapper;

	    @Override
	    public DriverResponse create(CreateDriverRequest request) {

	    	log.info(
	    		    "[CREATE_DRIVER] Creando conductor licencia={}",
	    		    request.licenseNumber()
	    		);
	    	
	    	Driver driver = driverMapper.toEntity(request);

	        Driver saved = driverRepository.save(driver);

	        log.info(
	        	    "[CREATE_DRIVER] Conductor creado id={}",
	        	    driver.getId()
	        	);
	        return driverMapper.toResponse(saved);
	    }

	    @Override
	    public List<DriverResponse> getActiveDrivers() {

	    	log.info("[GET_ACTIVE_DRIVERS] Consultando conductores activos");

	    	    List<DriverResponse> drivers =
	    	            driverRepository.findByActiveTrue()
	    	                    .stream()
	    	                    .map(driverMapper::toResponse)
	    	                    .toList();

	    	    log.info(
	    	    	    "[GET_ACTIVE_DRIVERS] Conductores encontrados={}",
	    	    	    drivers.size()
	    	    	);

	    	    return drivers;
	    }

	

}
