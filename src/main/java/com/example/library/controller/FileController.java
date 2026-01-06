package com.example.library.controller;

import com.example.library.services.FileService;
import jakarta.mail.Quota;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/file")
public class FileController {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private FileService fileService;
@PostMapping("/upload-file")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws IOException {
        return fileService.uploadFile(file);
    }

    @GetMapping("/download-file/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileName") String fileName ) throws IOException {
       return fileService.downloadFile(fileName);
    }







}
