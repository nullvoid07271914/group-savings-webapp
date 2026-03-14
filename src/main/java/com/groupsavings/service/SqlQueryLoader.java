package com.groupsavings.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class SqlQueryLoader {

	private final Map<String, String> queryCache = new ConcurrentHashMap<>();

	public String getQuery(String fileName) {
		return queryCache.computeIfAbsent(fileName, this::loadQuery);
	}

	private String loadQuery(String fileName) {
		try {
			ClassPathResource resource = new ClassPathResource("sql/" + fileName);

			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

				return reader.lines()
						.filter(line -> !line.trim().startsWith("--"))
						.filter(line -> !line.trim().isEmpty())
						.collect(Collectors.joining(" "))
						.replaceAll("\\s+", " ")
						.trim();
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to load SQL file: " + fileName, e);
		}
	}
}