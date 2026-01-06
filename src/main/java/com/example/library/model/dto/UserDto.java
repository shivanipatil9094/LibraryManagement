package com.example.library.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {


    private Long userId;


    private String userName;


    private String userEmail;

    private String userContact;
    private List<BookDto> books;
}
