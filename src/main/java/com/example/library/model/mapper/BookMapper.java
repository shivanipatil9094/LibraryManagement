package com.example.library.model.mapper;

import com.example.library.model.Book;

import com.example.library.model.dto.BookDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "book")
public interface BookMapper {

    BookDto BookResponse(Book book);
    Book BookRequest(BookDto categoryDto);

}
