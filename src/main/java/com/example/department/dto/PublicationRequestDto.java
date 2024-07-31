package com.example.department.dto;



public record PublicationRequestDto (
        String title,
        String author,
        String category,
        String date,
        String description,
        byte [] fileData,
        String fileName
){
}
