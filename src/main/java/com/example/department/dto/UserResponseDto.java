package com.example.department.dto;

import com.example.department.enums.UserRole;
import com.example.department.enums.UserStatus;

public record UserResponseDto(
        Long id,
        String email,
        UserStatus status,
        UserRole role
) {
}
