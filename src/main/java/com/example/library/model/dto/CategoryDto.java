package com.example.library.model.dto;

import com.example.library.model.Book;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {

    private Long categoryId;


    private String categoryName;

    private List<BookDto> books;

}
