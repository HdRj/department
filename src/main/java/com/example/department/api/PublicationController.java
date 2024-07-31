package com.example.department.api;

import com.example.department.dto.PublicationFullDto;
import com.example.department.dto.PublicationRequestDto;
import com.example.department.dto.PublicationResponseDto;
import com.example.department.dto.SearchResponseDto;
import com.example.department.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/publications")
public class PublicationController {
    private final PublicationService publicationService;

    @GetMapping("/")
    public ResponseEntity<List<PublicationResponseDto>> getAll(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "6") final int size
    ){
        try {
            return new ResponseEntity<>(publicationService.getAll(page,size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationFullDto> getById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(publicationService.getById(id),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("file/{id}")
    @ResponseBody
    public ResponseEntity<Resource>getFile(@PathVariable Long id){
        try{
            ByteArrayResource file = new ByteArrayResource(publicationService.getPdf(id));
            String fileName = publicationService.getFileName(id);
            return ResponseEntity.ok().header(CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(file);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<SearchResponseDto> search(
            @RequestParam(required = true) final String text,
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "6") final int size) {
        try{
            return new ResponseEntity<>(publicationService.search(text,page,size),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('manager_permission')")
    public ResponseEntity<PublicationFullDto> create(
            @RequestParam final String title,
            @RequestParam final String author,
            @RequestParam final String category,
            @RequestParam final String date,
            @RequestParam final String description,
            @RequestParam final MultipartFile file
    ){
        try{
            byte[] fileData = file.getBytes();
            if (fileData == null || fileData.length < 1) {
                throw new RuntimeException("Problem with the file. Please try again...");
            }
            String fileName=file.getOriginalFilename();
            PublicationRequestDto publicationRequestDto = new PublicationRequestDto(
                    title,
                    author,
                    category,
                    date,
                    description,
                    fileData,
                    fileName
            );
            return new ResponseEntity<>(publicationService.create(publicationRequestDto),HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('manager_permission')")
    public ResponseEntity<PublicationFullDto> change(
            @PathVariable final Long id,
            @RequestParam(required = false) final String title,
            @RequestParam(required = false) final String author,
            @RequestParam(required = false) final String category,
            @RequestParam(required = false) final String date,
            @RequestParam(required = false) final String description,
            @RequestParam(required = false) final MultipartFile file){
        try{
            byte[] fileData=null;
            String fileName=null;
            if(file!=null) {
                fileData = file.getBytes();
                fileName = file.getOriginalFilename();
            }
            PublicationRequestDto publicationRequestDto = new PublicationRequestDto(
                    title,
                    author,
                    category,
                    date,
                    description,
                    fileData,
                    fileName
            );
            return new ResponseEntity<>(publicationService.update(id, publicationRequestDto),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
