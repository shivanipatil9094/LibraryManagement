package com.example.library.controller;

import com.example.library.model.helper.Email;
import com.example.library.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
    @PostMapping("/send-mail")
    public Email sendMail(@RequestBody Email email){
       return emailService.sendEmail(email);
    }


    @PostMapping("/send-html-mail")
    public Email sendHtmlMail(@RequestBody Email email) throws MessagingException, IOException {
        return emailService.sendHtmlEmail(email);
    }


    @PostMapping("/send-email-file")
   public Email sendEmailFile(
            @RequestParam("to") String []to,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            @RequestParam("file") MultipartFile file

            ) throws MessagingException, IOException {
        Email email = new Email();
        System.out.println(to);
        email.setMessage(message);
        email.setHeader(subject);
        email.setTo(to);
        email.setFile(file);
        return emailService.sendEmailAttachment(email);





    }




}
