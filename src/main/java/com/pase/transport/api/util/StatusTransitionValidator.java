package com.pase.transport.api.util;

import java.util.List;
import java.util.Map;

import com.pase.transport.api.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class StatusTransitionValidator {

    private StatusTransitionValidator() {
    }

    private static final Map<OrderStatus, List<OrderStatus>> TRANSITIONS =
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

    public static void validateTransition(
            OrderStatus current,
            OrderStatus next) {

        if (current == null || next == null) {
            throw new BusinessException("Status cannot be null");
        }

        if (!TRANSITIONS.get(current).contains(next)) {

            log.warn(
                    "Transición inválida {} -> {}",
                    current,
                    next);

            throw new BusinessException(
                    "Invalid status transition from "
                            + current
                            + " to "
                            + next);
        }
    }

}
