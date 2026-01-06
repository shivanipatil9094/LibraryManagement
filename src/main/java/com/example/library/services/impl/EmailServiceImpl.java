package com.example.library.services.impl;

import com.example.library.model.helper.Email;
import com.example.library.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;
    @Override
    public Email sendEmail(Email email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email.getTo());
        simpleMailMessage.setSubject(email.getHeader());
        simpleMailMessage.setText(email.getMessage());
        simpleMailMessage.setFrom(from);
        javaMailSender.send(simpleMailMessage);

        return email;
    }

    @Override
    public Email sendHtmlEmail(Email email) throws MessagingException, IOException {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
       ClassPathResource resource = new ClassPathResource("static/index.html");        String message=new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage,true,"UTF-8");
        mimeMessageHelper.setTo(email.getTo());
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setSubject(email.getHeader());
        mimeMessageHelper.setText(message,true);
        javaMailSender.send(mimeMessage);
        return email;
    }

    @Override
    public Email sendEmailAttachment(Email email) throws MessagingException, IOException {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage ,true);
        mimeMessageHelper.setFrom(from);

        mimeMessageHelper.setText(email.getMessage());
        mimeMessageHelper.setSubject(email.getHeader());
        mimeMessageHelper.setTo(email.getTo());

        MultipartFile file = email.getFile();
        mimeMessageHelper.addAttachment(file.getOriginalFilename(),file);
        javaMailSender.send(mimeMessage);

        return email;
    }

    @Override
    public Email sendEmailAttachments(Email email) throws MessagingException, IOException {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage ,true);
        mimeMessageHelper.setFrom(from);

        mimeMessageHelper.setText(email.getMessage());
        mimeMessageHelper.setSubject(email.getHeader());
        mimeMessageHelper.setTo(email.getTo());
        email.getFiles().forEach(file -> {
            try {
                mimeMessageHelper.addAttachment(file.getName(),file);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });


        javaMailSender.send(mimeMessage);

        return email;
    }


}
