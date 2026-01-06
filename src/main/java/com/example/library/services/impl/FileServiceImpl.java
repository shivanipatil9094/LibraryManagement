package com.example.library.services.impl;

import com.example.library.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {
    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    public ResponseEntity<?> uploadFile(MultipartFile file) throws IOException {

            String fileName = file.getOriginalFilename();

            Path filePath=  Paths.get(uploadDir).resolve(fileName);

            Files.copy(file.getInputStream(),  filePath, StandardCopyOption.REPLACE_EXISTING);
            return ResponseEntity.ok().body("file uploaded successfully");

    }

    @Override
    public ResponseEntity<?> downloadFile(String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=/" + resource.getFilename())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(Files.size(filePath)).body(resource);
    }


    @Override
    public File getBook(String title) {
        //get the file path
        Path filePath = Paths.get(uploadDir).resolve(title).normalize();
        return new File(filePath.toUri());


    }
}
