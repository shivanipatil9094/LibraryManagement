package com.example.library.controller;

import com.example.library.model.dto.UserDto;

import com.example.library.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/user")
public class UserController {

        @Autowired

        private UserServices userServices;

        @PostMapping("/create-user")
        public ResponseEntity<?> createUser(@RequestBody UserDto category){

            try{
                return ResponseEntity.ok(userServices.createUser(category));
            }catch (Exception e) {
                System.out.println(e);
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("User already exist");
            }
        }


        @GetMapping("/get-User-by-id/{id}")
        //pathvariable store the data into url instead of requestparam store into sessions in headers
        public ResponseEntity<?> getUserById(@PathVariable("id") Long id){
            UserDto UserById = userServices.getUser(id);
            if(UserById!=null){
                return  ResponseEntity.ok(UserById);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not exist");
        }

        @DeleteMapping("/delete-user-by-id")

        public ResponseEntity<?> deleteUser(@RequestParam("id") Long id) {
        boolean isDeleted = userServices.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User with ID " + id + " does not exist");
    }





    }







