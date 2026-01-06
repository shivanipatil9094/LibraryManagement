package com.example.library.controller;

import com.example.library.model.dto.AuthorDto;
import com.example.library.model.dto.BookDto;
import com.example.library.model.dto.CategoryDto;
import com.example.library.services.AuthorServices;
import com.example.library.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/book")
public class BookController {


        @Autowired

        private BookServices bookServices;

        @PostMapping("/create-book")
        public ResponseEntity<?> createBook(
                @RequestParam("bookTitle") String bookTitle,
                @RequestParam("BookPublicationYear") int BookPublicationYear,
                @RequestParam("bookLanguage") String bookLanguage,
                @RequestParam("quantity") int quantity,
                @RequestParam("categoryId") Long categoryId,
                @RequestParam("authorId") Long authorId,
                @RequestParam("fileUpload") MultipartFile fileUpload,
                @RequestParam("fileAttach") MultipartFile fileAttach

        ){

            try{
                BookDto dto = new BookDto();
                dto.setBookTitle(bookTitle);
                dto.setBookLanguage(bookLanguage);
                dto.setFileUpload(fileUpload);
                dto.setFileAttach(fileAttach);
                dto.setQuantity(quantity);
                dto.setBookPublicationYear(BookPublicationYear);

                CategoryDto dto1 = new CategoryDto();
                dto1.setCategoryId(categoryId);
                dto.setCategory(dto1);

                AuthorDto dto2 = new AuthorDto();
                dto2.setAuthorId(authorId);

                dto.setAuthor(dto2);


                return ResponseEntity.ok(bookServices.createBook(dto));
            }catch (Exception e) {
                System.out.println(e);
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Book already exist");
            }
        }


        @GetMapping("/get-book-by-id/{id}")
        //path variable store the data into url instead of request param store into sessions in headers

        public ResponseEntity<?> getBookById(@PathVariable("id") Long id){
            BookDto BookById = bookServices.getBook(id);
            if(BookById!=null){
                return  ResponseEntity.ok(BookById);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book Not exist");
        }

    @DeleteMapping("/delete-book-by-id")
    public ResponseEntity<?> deleteBook(@RequestParam("id") Long id) {
        boolean isDeleted = bookServices.deleteBook(id);
        if (isDeleted) {
            return ResponseEntity.ok("Book deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Book with ID " + id + " does not exist");
    }





    }





