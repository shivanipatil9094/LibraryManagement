package com.example.library.services;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.dto.AuthorDto;
import com.example.library.model.dto.BookDto;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface BookServices {

    BookDto createBook(BookDto book) throws IOException, MessagingException;
    BookDto getBook(Long id);

    Boolean deleteBook(Long id);


}
