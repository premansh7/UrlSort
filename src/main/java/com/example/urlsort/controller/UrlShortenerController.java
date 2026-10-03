package com.example.urlsort.controller;

import com.example.urlsort.dto.ShortenRequest;
import com.example.urlsort.dto.ShortenResponse;
import com.example.urlsort.model.UrlMapping;
import com.example.urlsort.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "URL Shortener API", description = "Endpoints for creating and managing short URLs")
public class UrlShortenerController {

    private final UrlShortenerService service;

    @PostMapping("/shorten")
    @Operation(summary = "Create short URL", description = "Generates a short URL for a given original URL")
    public ResponseEntity<ShortenResponse> shorten(@Valid @RequestBody ShortenRequest request) {
        UrlMapping mapping = service.createShortUrl(request.getOriginalUrl());
        ShortenResponse response = ShortenResponse.builder()
                .shortCode(mapping.getShortCode())
                .shortUrl(service.buildShortUrl(mapping.getShortCode()))
                .originalUrl(mapping.getOriginalUrl())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/stats/{shortCode}")
    @Operation(summary = "Get URL statistics", description = "Retrieves statistics for a specific short URL")
    public ResponseEntity<UrlMapping> stats(@PathVariable String shortCode) {
        return ResponseEntity.ok(service.getStats(shortCode));
    }
}