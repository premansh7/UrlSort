package com.example.urlsort.controller;

import com.example.urlsort.model.UrlMapping;
import com.example.urlsort.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Tag(name = "Redirect API", description = "Endpoints for redirecting short URLs to original URLs")
public class RedirectController {

    private final UrlShortenerService service;

    @GetMapping("/{shortCode:[a-zA-Z0-9]{7}}")
    @Operation(summary = "Redirect to original URL", description = "Resolves the short code and redirects to the original URL")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        UrlMapping mapping = service.resolve(shortCode);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, mapping.getOriginalUrl());
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}