package com.example.department.repository;

import com.example.department.dto.PublicationResponseDto;
import com.example.department.model.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PublicationRepository extends JpaRepository<Publication,Long> {
    @Query("SELECT new com.example.department.dto.PublicationResponseDto(p.id, p.title, p.author, p.category, p.date, p.description) " +
            "FROM Publication p")
    Page<PublicationResponseDto> findAllPublicationsWithoutFile(Pageable pageable);

    @Query("SELECT new com.example.department.dto.PublicationResponseDto(p.id, p.title, p.author, p.category, p.date, p.description) " +
            "FROM Publication p WHERE (p.title LIKE %:text%) " +
            "OR (p.category LIKE %:text%)" +
            "OR (p.description LIKE %:text%)")
    Page<PublicationResponseDto> searchByText(@Param("text") String text, Pageable pageable);
}
