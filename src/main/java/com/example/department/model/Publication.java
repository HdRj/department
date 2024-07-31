package com.example.department.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Calendar;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Table(name = "publication")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
public class Publication {

    @Id
    @GeneratedValue(strategy = AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(updatable = false)
    private Long id;

    @Version
    @Column(updatable = true)
    Long version;

    @Column(name = "title", length = 120)
    @Setter
    private String title;

    @Column(name = "author")
    @Setter
    private String author;

    @Column(name = "category",length = 50)
    @Setter
    private String category;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    @Setter
    private Calendar date;

    @Column(name = "description", columnDefinition = "TEXT")
    @Setter
    private  String description;

    @Lob
    @Column(name = "file_data", length = 10485760, nullable = false)
    @Setter
    private byte[] fileData;

    @Column(name = "file_original_name", nullable = false)
    @Setter
    String fileOriginalName;

    public Publication(
            String title,
            String author,
            String category,
            Calendar date,
            String description,
            byte[] fileData,
            String fileOriginalName) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.date = date;
        this.description = description;
        this.fileData = fileData;
        this.fileOriginalName=fileOriginalName;
    }


}
