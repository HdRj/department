package com.example.department.mapper;

import com.example.department.dto.PublicationFullDto;
import com.example.department.dto.PublicationRequestDto;
import com.example.department.exceptions.CalendarException;
import com.example.department.model.Publication;
import com.example.department.utils.CalendarUtil;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PublicationMapper {
    public PublicationFullDto toFullDto(Publication entity){
        return new PublicationFullDto(
                entity.getId(),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getCategory(),
                CalendarUtil.getDate(entity.getDate()),
                entity.getDescription(),
                entity.getFileData(),
                entity.getFileOriginalName()
        );
    }


    public Publication toEntity(PublicationRequestDto dto) throws CalendarException {
        return new Publication(
                dto.title(),
                dto.author(),
                dto.category(),
                CalendarUtil.getCalendar(dto.date()),
                dto.description(),
                dto.fileData(),
                dto.fileName()
        );
    }
}
