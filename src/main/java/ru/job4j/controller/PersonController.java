package ru.job4j.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.domain.ErrorBody;
import ru.job4j.domain.Person;
import ru.job4j.exception.PersonNotFoundException;
import ru.job4j.service.impl.SimplePersonService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final SimplePersonService personService;

    @GetMapping("/")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        return ResponseEntity.ok(personService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account is not found. PLease, check requisites")));
    }

    @PostMapping("/")
    public ResponseEntity<Person> save(@RequestBody Person person) {
        return new ResponseEntity<>(
                personService.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        personService.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        personService.delete(person);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = {PersonNotFoundException.class})
    public ResponseEntity<ErrorBody> handleNotValidStatusException(PersonNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(400)
                .body(ErrorBody.builder()
                        .statusCode(400)
                        .message("Person not exists")
                        .details(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

}
