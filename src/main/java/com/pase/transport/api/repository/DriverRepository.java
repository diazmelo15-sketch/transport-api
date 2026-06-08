package com.pase.transport.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pase.transport.api.entity.Driver;

public interface DriverRepository extends JpaRepository<Driver, UUID>  {

	 List<Driver> findByActiveTrue();
}
