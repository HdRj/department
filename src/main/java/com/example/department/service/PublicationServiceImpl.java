package com.example.department.service;

import com.example.department.dto.PublicationFullDto;
import com.example.department.dto.PublicationRequestDto;
import com.example.department.dto.PublicationResponseDto;
import com.example.department.dto.SearchResponseDto;
import com.example.department.exceptions.CalendarException;
import com.example.department.mapper.PublicationMapper;
import com.example.department.model.Publication;
import com.example.department.repository.PublicationRepository;
import com.example.department.utils.CalendarUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicationServiceImpl implements PublicationService{
    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;


    @Override
    public List<PublicationResponseDto> getAll(int page, int size) {
        Sort sort = Sort.by("date").descending();
        Pageable paging = PageRequest.of(page, size,sort);
        return publicationRepository.findAllPublicationsWithoutFile(paging).getContent();
    }

    @Override
    public PublicationFullDto getById(Long id) {
        Optional<Publication>publicationOptional = publicationRepository.findById(id);
        if(publicationOptional.isPresent()){
            return publicationMapper.toFullDto(publicationOptional.get());
        }else {
            log.error("getById: Publication with id="+id+" doesn't exist");
            throw new RuntimeException("Publication with id="+id+" doesn't exist");
        }
    }

    @Override
    public byte[] getPdf(Long id) {
        Optional<Publication>publicationOptional = publicationRepository.findById(id);
        if(publicationOptional.isPresent()){
            return publicationOptional.get().getFileData();
        }else {
            log.error("getPdf: Publication with id="+id+" doesn't exist");
            throw new RuntimeException("Publication with id="+id+" doesn't exist");
        }
    }

    @Override
    public String getFileName(Long id){
        Optional<Publication>publicationOptional = publicationRepository.findById(id);
        if(publicationOptional.isPresent()){
            return publicationOptional.get().getFileOriginalName();
        }else {
            log.error("getFileName: Publication with id="+id+" doesn't exist");
            throw new RuntimeException("Publication with id="+id+" doesn't exist");
        }
    }


    @Override
    @Transactional
    public PublicationFullDto create(PublicationRequestDto publicationRequestDto) throws CalendarException {
        Publication publication = publicationMapper.toEntity(publicationRequestDto);
        log.info("New publication was created, title="+publication.getTitle());
        return publicationMapper.toFullDto(publicationRepository.save(publication));
    }



    @Override
    public PublicationFullDto update(Long id, PublicationRequestDto publicationRequestDto) throws CalendarException {
        Optional<Publication>publicationOptional = publicationRepository.findById(id);
        if(publicationOptional.isPresent()){
            Publication publication = publicationOptional.get();
            if(!isEmpty(publicationRequestDto.title())){
                publication.setTitle(publicationRequestDto.title());
            }
            if(!isEmpty(publicationRequestDto.author())){
                publication.setAuthor(publicationRequestDto.author());
            }
            if(!isEmpty(publicationRequestDto.category())){
                publication.setCategory(publicationRequestDto.category());
            }
            if(!isEmpty(publicationRequestDto.date())){
                publication.setDate(CalendarUtil.getCalendar(publicationRequestDto.date()));
            }
            if(!isEmpty(publicationRequestDto.description())){
                publication.setDescription(publicationRequestDto.description());
            }
            if(publicationRequestDto.fileData() != null && publicationRequestDto.fileData().length > 1){
                publication.setFileData(publicationRequestDto.fileData());
            }
            if(!isEmpty(publicationRequestDto.fileName())){
                publication.setFileOriginalName(publicationRequestDto.fileName());
            }
            log.info("The publication was updated, id="+publication.getId());
            return publicationMapper.toFullDto(publicationRepository.save(publication));
        }else {
            throw new RuntimeException("Publication with id="+id+" doesn't exist");
        }
    }

    @Override
    public SearchResponseDto search(String text, int page, int size){
        Sort sort = Sort.by("date").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PublicationResponseDto> publicationPage = publicationRepository.searchByText(text, pageable);

        return new SearchResponseDto<>(
                publicationPage.getContent(),
                publicationPage.getTotalElements(),
                publicationPage.getTotalPages(),
                publicationPage.getSize(),
                publicationPage.getNumber()
        );
    }

    private boolean isEmpty(String s){
        if(s==null  || s.isEmpty() ){
            return true;
        }
        return false;
    }
}
