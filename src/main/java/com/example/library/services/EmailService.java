package com.example.library.services;

import com.example.library.model.helper.Email;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface EmailService {
    Email sendEmail(Email email);

    Email sendHtmlEmail(Email email) throws MessagingException, IOException;

    Email sendEmailAttachment(Email email) throws MessagingException, IOException;
    Email sendEmailAttachments(Email email) throws MessagingException, IOException;


}
