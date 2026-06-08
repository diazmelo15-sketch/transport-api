package com.pase.transport.api.service;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.pase.transport.api.dto.AssignOrderRequest;
import com.pase.transport.api.dto.AssignmentResponse;

public interface AssignmentService {

	AssignmentResponse assignDriver(AssignOrderRequest request);

	AssignmentResponse uploadPdf(UUID assignmentId, MultipartFile file);

	AssignmentResponse uploadImage(UUID assignmentId, MultipartFile file);

}
