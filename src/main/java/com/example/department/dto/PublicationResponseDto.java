package com.example.department.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Calendar;

public record PublicationResponseDto(
        Long id,
        String title,
        String author,
        String category,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        Calendar date,
        String description
    ) {

}
