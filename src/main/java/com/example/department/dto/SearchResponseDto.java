package com.example.department.dto;

import java.util.List;

public record SearchResponseDto<T>(
        List<T> content,
        Long totalElements,
        Integer totalPages,
        Integer size,
        Integer number
) {

}
