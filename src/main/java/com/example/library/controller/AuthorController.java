package com.example.library.controller;

import com.example.library.model.dto.AuthorDto;
import com.example.library.services.AuthorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
public class AuthorController {
@Autowired

        private AuthorServices authorServices;

        @PostMapping("/create-author")
        public ResponseEntity<?> createAuthor(@RequestBody AuthorDto author){

                try{
                        return ResponseEntity.ok(authorServices.createAuthor(author));
                }catch (Exception e) {
                        System.out.println(e);
                        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Author already exist");
                }
        }


        @GetMapping("/get-author-by-id/{id}")

        //pathvariable store the data into url instead of requestparam store into sessions in headers

        public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id){
                AuthorDto auhtorById = authorServices.getAuthor(id);
                if(auhtorById!=null){
                        return  ResponseEntity.ok(auhtorById);
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author Not exist");
        }

        @DeleteMapping("/delete-author-by-id")
        public ResponseEntity<?> deleteAuthor(@RequestParam("id") Long id) {
                boolean isDeleted = authorServices.deleteAuthor(id);
                if (isDeleted) {
                        return ResponseEntity.ok("Author deleted successfully");
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Author with ID " + id + " does not exist");
        }





}
