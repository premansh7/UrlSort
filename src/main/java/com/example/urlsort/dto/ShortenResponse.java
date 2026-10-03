package com.example.urlsort.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortenResponse {
    private String shortCode;
    private String shortUrl;
    private String originalUrl;
}