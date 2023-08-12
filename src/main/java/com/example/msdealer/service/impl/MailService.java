package com.example.msdealer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    public void mailSender(String messageTo) throws MessagingException, IOException {
//        sendEmail();
        sendEmail(messageTo);
        log.info("Email sent successfully!");
    }

    void sendEmail(String messageTo) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(messageTo);
        msg.setFrom("talehrzayev000@gmail.com");
        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");
        javaMailSender.send(msg);
    }

    void sendEmailWithAttachment(String messageTo) throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(messageTo);
        helper.setFrom("talehrzayev000@gmail.com");
        helper.setSubject("Register in Ecommerce");
        helper.setText("<h1>%s You register succesfully</h1>",messageTo);

        javaMailSender.send(msg);
    }
}
