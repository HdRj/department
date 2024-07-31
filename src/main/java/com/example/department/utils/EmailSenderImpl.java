package com.example.department.utils;

import com.example.department.exceptions.EmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Component
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender{
    private final JavaMailSender javaMailSender;
    @Override
    @Async
    public CompletableFuture<Boolean> sendSimpleMail(
            String recipient,
            String msgBody,
            String subject
    ) throws EmailException {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        try {
            mailMessage.setTo(recipient);
            mailMessage.setText(msgBody);
            mailMessage.setSubject(subject);
            // Sending the mail
            javaMailSender.send(mailMessage);
        }catch (Exception e){
            throw new EmailException(e.getMessage());
        }
        return completedFuture(true);
    }
}
