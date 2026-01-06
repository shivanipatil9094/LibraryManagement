package com.example.library.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

@Service
public interface FileService {

    ResponseEntity<?> uploadFile(MultipartFile file) throws IOException;

    ResponseEntity<?> downloadFile(String  fileName) throws IOException;
    File getBook(String title);


}
