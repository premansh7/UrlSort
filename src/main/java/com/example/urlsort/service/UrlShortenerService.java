package com.example.urlsort.service;

import com.example.urlsort.exception.UrlNotFoundException;
import com.example.urlsort.model.UrlMapping;
import com.example.urlsort.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
public class UrlShortenerService {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 7;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final UrlMappingRepository repository;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    public UrlShortenerService(UrlMappingRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UrlMapping createShortUrl(String originalUrl) {
        String shortCode = generateUniqueCode();
        UrlMapping mapping = UrlMapping.builder()
                .shortCode(shortCode)
                .originalUrl(originalUrl)
                .build();
        return repository.save(mapping);
    }

    @Transactional
    public UrlMapping resolve(String shortCode) {
        UrlMapping mapping = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException(shortCode));
        mapping.incrementClickCount();
        return repository.save(mapping);
    }

    @Transactional(readOnly = true)
    public UrlMapping getStats(String shortCode) {
        return repository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException(shortCode));
    }

    public String buildShortUrl(String shortCode) {
        return baseUrl + "/" + shortCode;
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = randomCode();
        } while (repository.existsByShortCode(code));
        return code;
    }

    private String randomCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}