package com.example.library.model.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BookDto {


    private Long bookId;


    private String bookTitle;

    private String bookLanguage;

    private int BookPublicationYear;


    private CategoryDto category;


    private AuthorDto author;
    private int quantity;


    private MultipartFile fileUpload;
    private MultipartFile fileAttach;
}
