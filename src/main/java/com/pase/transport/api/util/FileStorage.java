package com.pase.transport.api.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileStorage {

	@Value("${file.upload.dir}")
	private String uploadDir;
	
	public  String saveFile(MultipartFile file, String folder) {

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

			throw new RuntimeException("Error guardando archivo", e);
		}
	}
}
