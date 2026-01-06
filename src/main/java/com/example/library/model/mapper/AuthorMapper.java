package com.example.library.model.mapper;

import com.example.library.model.Author;
import com.example.library.model.Category;
import com.example.library.model.dto.AuthorDto;
import com.example.library.model.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "author")
public interface AuthorMapper {

    AuthorDto AuthorResponse(Author author);
    Author AuthorRequest(AuthorDto authorDto);
}
