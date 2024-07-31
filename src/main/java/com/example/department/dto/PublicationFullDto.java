package com.example.department.dto;

import java.util.Calendar;

public record PublicationFullDto(
        Long id,
        String title,
        String author,
        String category,
        String date,
        String description,
        byte[] fileData,
        String fileName
) {
}
