package com.example.department.utils;

import com.example.department.exceptions.EmailException;

import java.util.concurrent.CompletableFuture;

public interface EmailSender {

    CompletableFuture<Boolean> sendSimpleMail(
            String recipient,
            String msgBody,
            String subject
    ) throws EmailException;
}
