package com.pase.transport.api.controller;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pase.transport.api.dto.AssignOrderRequest;
import com.pase.transport.api.dto.AssignmentResponse;
import com.pase.transport.api.service.AssignmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
@Tag(name = "Assignment", description = "Asignación de Ordenes")
public class AssignmentController {

	private final AssignmentService service;

	@PostMapping
	@Operation(summary = "Asignar un conductor a una orden")
	public ResponseEntity<AssignmentResponse> assignDriver(@Valid @RequestBody AssignOrderRequest request) {

		return ResponseEntity.ok(service.assignDriver(request));
	}

	@PostMapping(value = "/{assignmentId}/pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Agregar un archivo a la asignación (PDF)")
	public ResponseEntity<AssignmentResponse> uploadPdf(@PathVariable UUID assignmentId,
			@RequestPart("file") MultipartFile file) {

		AssignmentResponse response = service.uploadPdf(assignmentId, file);
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/{assignmentId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Agregar una imagen (.png, .jpg)")
	public ResponseEntity<AssignmentResponse> uploadImage(@PathVariable UUID assignmentId, @RequestPart MultipartFile file) {

		AssignmentResponse response = service.uploadImage(assignmentId, file);

		return ResponseEntity.ok(response);
	}
}
