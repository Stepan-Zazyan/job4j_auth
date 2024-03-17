package ru.job4j.dto;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;

@Data
public class PersonDto {

    @NotBlank(message = "Username must be not empty")
    @UniqueElements(message = "username must be unique")
    private String username;
    @NotBlank(message = "Username must be not empty")
    private String password;

}
