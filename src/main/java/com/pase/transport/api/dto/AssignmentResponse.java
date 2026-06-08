package com.pase.transport.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AssignmentResponse(
		UUID id,
        UUID orderId,
        UUID driverId,
        String driverName,
        String pdfFileName,
        String imageFileName,
        LocalDateTime assignedAt) {

}
