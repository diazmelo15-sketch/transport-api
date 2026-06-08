package com.pase.transport.api.service;

import java.util.List;

import com.pase.transport.api.dto.CreateDriverRequest;
import com.pase.transport.api.dto.DriverResponse;

public interface DriverService {

	 DriverResponse create(CreateDriverRequest request);
	 
	 List<DriverResponse>getActiveDrivers();
}
