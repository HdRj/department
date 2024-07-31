package com.example.department.dto;

public record ChangePasswordDto(
        String oldPassword,
        String newPassword,
        String newPassword2
) {
}
