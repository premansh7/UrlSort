package com.example.urlsort.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortenRequest {

    @NotBlank(message = "originalUrl must not be blank")
    @Pattern(regexp = "^(https?)://.*", message = "originalUrl must start with http:// or https://")
    private String originalUrl;
}