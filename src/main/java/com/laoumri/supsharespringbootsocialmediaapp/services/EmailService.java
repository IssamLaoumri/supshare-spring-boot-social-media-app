package com.laoumri.supsharespringbootsocialmediaapp.services;

import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface EmailService {
    void sendEmail(String to, String subject, String template, Context context) throws MessagingException;
}
