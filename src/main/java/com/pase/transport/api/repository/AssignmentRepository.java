package com.pase.transport.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pase.transport.api.entity.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {

	 boolean existsByOrderId(UUID orderId);
}
