package com.example.department.service;

import com.example.department.dto.PublicationFullDto;
import com.example.department.dto.PublicationRequestDto;
import com.example.department.dto.PublicationResponseDto;
import com.example.department.dto.SearchResponseDto;
import com.example.department.exceptions.CalendarException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PublicationService {
    List<PublicationResponseDto> getAll(int page, int size);
    PublicationFullDto getById(Long id);
    byte[] getPdf(Long id);
    String getFileName(Long id);
    @Transactional
    PublicationFullDto create(PublicationRequestDto publicationRequestDto) throws CalendarException;
    @Transactional
    PublicationFullDto update(Long id, PublicationRequestDto publicationRequestDto) throws CalendarException;
    SearchResponseDto search(String text, int page, int size);
}
