package com.example.library.controller;

import com.example.library.model.dto.BookDto;
import com.example.library.model.dto.CategoryDto;
import com.example.library.services.BookServices;
import com.example.library.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired

        private CategoryServices CategoryServices;

        @PostMapping("/create-category")
        public ResponseEntity<?> createCategory(@RequestBody CategoryDto category){

            try{
                return ResponseEntity.ok(CategoryServices.createCategory(category));
            }catch (Exception e) {
                System.out.println(e);
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Category already exist");
            }
        }


        @GetMapping("/get-category-by-id/{id}")
        //pathvariable store the data into url instead of requestparam store into sessions in headers
        public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id){
            CategoryDto CategoryById = CategoryServices.getCategory(id);
            if(CategoryById!=null){
                return  ResponseEntity.ok(CategoryById);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category Not exist");
        }

    @DeleteMapping("/delete-category-by-id")
    public ResponseEntity<?> deleteCategory(@RequestParam("id") Long id) {
        boolean isDeleted = CategoryServices.deleteCategory(id);
        if (isDeleted) {
            return ResponseEntity.ok("Category deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Category with ID " + id + " does not exist");
    }





    }





