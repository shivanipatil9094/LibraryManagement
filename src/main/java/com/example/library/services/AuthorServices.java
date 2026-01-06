package com.example.library.services;

import com.example.library.model.Author;
import com.example.library.model.dto.AuthorDto;

public interface AuthorServices {

         AuthorDto createAuthor(AuthorDto author);
         AuthorDto getAuthor(Long id);
         Boolean deleteAuthor(Long id);

}
