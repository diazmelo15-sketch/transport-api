package com.pase.transport.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pase.transport.api.exception.FileStorageException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

	@Value("${file.upload.dir}")
	private String uploadDir;

	@Override
	public String saveFile(MultipartFile file, String folder) {

		if (file == null || file.isEmpty()) {
			return null;
		}

		try {

			Path directory = Paths.get(uploadDir, folder);

			if (!Files.exists(directory)) {
				Files.createDirectories(directory);
			}

			String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

			Path filePath = directory.resolve(fileName);

			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			return filePath.toString();

		} catch (IOException e) {

			log.error("Error guardando archivo {}", file.getOriginalFilename(), e);

			throw new FileStorageException("No fue posible almacenar el archivo", e);

		}
	}

}
