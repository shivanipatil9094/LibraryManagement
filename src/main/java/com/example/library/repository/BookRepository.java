package com.example.library.repository;

import com.example.library.model.Book;
import com.example.library.model.dto.BookDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {

}
