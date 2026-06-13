package com.pase.transport.api.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.pase.transport.api.entity.Order;
import jakarta.persistence.criteria.Predicate;

public class OrderSpecification {

	private OrderSpecification() {
	}

	public static Specification<Order> filter(OrderStatus status, String origin, String destination,
			LocalDate startDate, LocalDate endDate) {

		return (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<>();

			if (status != null) {
				predicates.add(cb.equal(root.get("status"), status));
			}

			if (origin != null && !origin.isBlank()) {
				predicates.add(cb.like(cb.lower(root.get("origin")), "%" + origin.toLowerCase() + "%"));
			}

			if (destination != null && !destination.isBlank()) {
				predicates.add(cb.like(cb.lower(root.get("destination")), "%" + destination.toLowerCase() + "%"));
			}
			if (startDate != null) {
				predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), startDate.atStartOfDay()));
			}

			if (endDate != null) {
				predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), endDate.atTime(LocalTime.MAX)));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
