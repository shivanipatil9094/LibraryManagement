package com.example.library.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class    Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false)
    private String bookTitle;

    private String bookLanguage;

    private int BookPublicationYear;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Author author;
    private int quantity;


}