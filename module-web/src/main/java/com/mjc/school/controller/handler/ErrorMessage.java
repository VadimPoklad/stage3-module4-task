package com.mjc.school.controller.handler;


import java.time.LocalDateTime;

public record ErrorMessage(
        int code,
        String massage,
        String exceptionName,
        LocalDateTime timestamp

) {
    public ErrorMessage(int code, String exceptionName, String massage) {
        this(code, exceptionName, massage, LocalDateTime.now());
    }
}
