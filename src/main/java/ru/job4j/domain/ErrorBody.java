package ru.job4j.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorBody {
    private int statusCode;
    private String message;
    private String details;
    private LocalDateTime timestamp;
}
