package com.groupsavings.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Value("${file.allowed-types}")
	private String[] allowedTypes;

	/**
	 * Store payment receipt in resources/contribution/{memberCode}/
	 */
	public String storePaymentReceipt(MultipartFile file, String memberCode) throws IOException {
		// Validate file
		validateFile(file);

		// Create directory path: resources/contribution/{memberCode}/
		String directoryPath = uploadDir + "/" + memberCode;
		Path uploadPath = Paths.get(directoryPath);

		// Create directory if it doesn't exist
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		// Generate unique filename
		String originalFilename = file.getOriginalFilename();
		String fileExtension = getFileExtension(originalFilename);
		String filename = generateUniqueFilename(memberCode, fileExtension);

		// Full file path
		Path filePath = uploadPath.resolve(filename);

		// Save file
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		// Return relative path for database storage
		return "/uploads/contributions/" + memberCode + "/" + filename;
	}

	/**
	 * Validate file type and size
	 */
	private void validateFile(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("File is empty");
		}

		String contentType = file.getContentType();
		boolean isValidType = false;

		for (String allowedType : allowedTypes) {
			if (allowedType.equals(contentType)) {
				isValidType = true;
				break;
			}
		}

		if (!isValidType) {
			throw new IllegalArgumentException("Invalid file type. Allowed: " + String.join(", ", allowedTypes));
		}
	}

	/**
	 * Get file extension
	 */
	private String getFileExtension(String filename) {
		if (filename == null || filename.lastIndexOf(".") == -1) {
			return "";
		}
		return filename.substring(filename.lastIndexOf("."));
	}

	/**
	 * Generate unique filename
	 */
	private String generateUniqueFilename(String memberCode, String extension) {
		String timestamp = String.valueOf(System.currentTimeMillis());
		String uuid = UUID.randomUUID().toString().substring(0, 8);
		return memberCode + "_" + timestamp + "_" + uuid + extension;
	}
}