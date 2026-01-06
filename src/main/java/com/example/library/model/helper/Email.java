package com.example.library.model.helper;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Data
public class Email {
      private String from;
      private  String []to;
      private String header;
      private  String  message;
      private MultipartFile file;
      private List<File> files;



}
