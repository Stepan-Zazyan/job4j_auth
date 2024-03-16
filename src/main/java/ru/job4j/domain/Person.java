package ru.job4j.domain;

import lombok.Data;
import ru.job4j.exception.Operation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Username must be not empty")
    private String username;
    @NotBlank(message = "Password must be not empty", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class
    })
    private String password;
}
