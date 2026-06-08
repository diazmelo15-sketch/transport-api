package com.pase.transport.api.service;


import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pase.transport.api.dto.AssignOrderRequest;
import com.pase.transport.api.dto.AssignmentResponse;
import com.pase.transport.api.entity.Assignment;
import com.pase.transport.api.entity.Driver;
import com.pase.transport.api.entity.Order;
import com.pase.transport.api.util.OrderStatus;
import com.pase.transport.api.exception.BusinessException;
import com.pase.transport.api.exception.ResourceNotFoundException;
import com.pase.transport.api.mapper.AssignmentMapper;
import com.pase.transport.api.repository.AssignmentRepository;
import com.pase.transport.api.repository.DriverRepository;
import com.pase.transport.api.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssignmentServiceImpl implements AssignmentService {

	private final AssignmentRepository assignmentRepository;
	private final OrderRepository orderRepository;
	private final DriverRepository driverRepository;
	private final AssignmentMapper assignmentMapper;
	private final FileStorageService fileStorageService;


	@Override
	public AssignmentResponse assignDriver(AssignOrderRequest request) {

		log.info("[ASSIGN_DRIVER] Iniciando asignación de conductor. orderId={} driverId={}", request.orderId(),
				request.driverId());

		Order order = orderRepository.findById(request.orderId())
				.orElseThrow(() -> new ResourceNotFoundException("Order no encontrada con id: " + request.orderId()));

		Driver driver = driverRepository.findById(request.driverId())
				.orElseThrow(() -> new ResourceNotFoundException("Driver no encontrado con id: " + request.driverId()));

		if (!driver.isActive()) {
			log.warn("[ASSIGN_DRIVER] El conductor se encuentra inactivo. driverId={}", driver.getId());

			throw new BusinessException("El conductor se encuentra inactivo");
		}

		if (order.getStatus() != OrderStatus.CREATED) {

			log.warn("[ASSIGN_DRIVER] La orden no está en estado CREATED. orderId={} estado={}", order.getId(),
					order.getStatus());

			throw new BusinessException("La orden debe estar en estado CREATED");
		}

		if (assignmentRepository.existsByOrderId(order.getId())) {

			log.warn("[ASSIGN_DRIVER] La orden ya tiene un conductor asignado. orderId={}", order.getId());
			throw new BusinessException("La orden ya tiene un conductor asignado");
		}

		Assignment assignment = Assignment.builder().order(order).driver(driver).assignedAt(LocalDateTime.now())
				.build();

		assignment = assignmentRepository.save(assignment);

		log.info("[ASSIGN_DRIVER] Asignación creada exitosamente. assignmentId={}", assignment.getId());

		return assignmentMapper.toResponse(assignment);
	}

	@Override
	public AssignmentResponse uploadPdf(UUID assignmentId, MultipartFile file) {

		log.info("[UPLOAD_PDF] Iniciando carga de PDF. assignmentId={}", assignmentId);

		Assignment assignment = assignmentRepository.findById(assignmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada con id: " + assignmentId));

		if (file == null || file.isEmpty()) {

			log.warn("[UPLOAD_PDF] Archivo PDF vacío. assignmentId={}", assignmentId);

			throw new BusinessException("Debe seleccionar un archivo PDF");
		}

		if (!"application/pdf".equalsIgnoreCase(file.getContentType())) {

			log.warn("[UPLOAD_PDF] Tipo de archivo inválido. assignmentId={} contentType={}", assignmentId,
					file.getContentType());

			throw new BusinessException("Solo se permiten archivos PDF");
		}

		String pdfPath = fileStorageService.saveFile(file, "pdf");

		assignment.setPdfFileName(file.getOriginalFilename());
		assignment.setPdfPath(pdfPath);

		assignmentRepository.save(assignment);

		log.info("[UPLOAD_PDF] PDF cargado exitosamente. assignmentId={} archivo={}", assignmentId,
				file.getOriginalFilename());

		return assignmentMapper.toResponse(assignment);
	}

	@Override
	public AssignmentResponse uploadImage(UUID assignmentId, MultipartFile file) {

		log.info("[UPLOAD_IMAGE] Iniciando carga de imagen. assignmentId={}", assignmentId);

		Assignment assignment = assignmentRepository.findById(assignmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada con id: " + assignmentId));

		if (file == null || file.isEmpty()) {

			log.warn("[UPLOAD_IMAGE] Archivo vacío. assignmentId={}", assignmentId);

			throw new BusinessException("Debe seleccionar una imagen");
		}

		String contentType = file.getContentType();

		if (!"image/png".equalsIgnoreCase(contentType) && !"image/jpeg".equalsIgnoreCase(contentType)) {

			log.warn("[UPLOAD_IMAGE] Tipo de archivo inválido. assignmentId={} contentType={}", assignmentId,
					contentType);

			throw new BusinessException("Solo se permiten imágenes JPG o PNG");
		}

		String imagePath = fileStorageService.saveFile(file, "image");

		assignment.setImageFileName(file.getOriginalFilename());
		assignment.setImagePath(imagePath);

		assignment = assignmentRepository.save(assignment);

		log.info("[UPLOAD_IMAGE] Imagen cargada correctamente. assignmentId={} archivo={}", assignmentId,
				file.getOriginalFilename());

		return assignmentMapper.toResponse(assignment);
	}

	

}
