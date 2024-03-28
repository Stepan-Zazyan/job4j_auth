package ru.job4j.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.job4j.domain.ErrorBody;
import ru.job4j.exception.PersonNotFoundException;
import ru.job4j.exception.UsernameIsTakenException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(
                e.getFieldErrors().stream()
                        .map(f -> Map.of(
                                f.getField(),
                                String.format("%s. Actual value: %s", f.getDefaultMessage(), f.getRejectedValue())
                        ))
                        .collect(Collectors.toList())
        );
    }

    @ExceptionHandler({PersonNotFoundException.class})
    public ResponseEntity<?> handleNPE(PersonNotFoundException exception) {
        return ResponseEntity.status(400)
                .body(ErrorBody.builder()
                        .statusCode(400)
                        .message("Person Not Found")
                        .details(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({UsernameIsTakenException.class})
    public ResponseEntity<?> handleNPE(UsernameIsTakenException exception) {
        return ResponseEntity.status(400)
                .body(ErrorBody.builder()
                        .statusCode(400)
                        .message("Sorry, Username Is Taken")
                        .details(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex) {
        return ResponseEntity.status(400)
                .body(ErrorBody.builder()
                        .statusCode(400)
                        .message("Sorry, can not do this operation")
                        .details(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

}
