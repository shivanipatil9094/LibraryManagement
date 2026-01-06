package com.example.library.model.dto;

import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class AuthorDto{

    private Long authorId;

    private String authorName;

    private String authorGenre;

    private List<BookDto> book;

}
