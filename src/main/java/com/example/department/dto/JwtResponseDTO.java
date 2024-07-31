package com.example.department.dto;

import lombok.Builder;

@Builder
public record JwtResponseDTO(
        String accessToken
) {
}
