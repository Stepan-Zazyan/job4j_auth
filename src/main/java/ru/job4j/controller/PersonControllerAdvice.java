package ru.job4j.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.job4j.domain.ErrorBody;

import java.time.LocalDateTime;

@ControllerAdvice
public class PersonControllerAdvice {

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<?> handleNPE(NullPointerException exception) {
        return ResponseEntity.status(400)
                .body(ErrorBody.builder()
                        .statusCode(400)
                        .message("Sorry, you got NPE")
                        .details(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

}
