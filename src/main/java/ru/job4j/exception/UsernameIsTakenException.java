package ru.job4j.exception;

public class UsernameIsTakenException extends Exception {
    public UsernameIsTakenException(String message) {
        super(message);
    }
}
