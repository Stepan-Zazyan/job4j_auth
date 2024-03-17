package ru.job4j.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.domain.ErrorBody;
import ru.job4j.domain.Person;
import ru.job4j.dto.PersonDto;
import ru.job4j.exception.Operation;
import ru.job4j.exception.PersonNotFoundException;
import ru.job4j.service.impl.SimplePersonService;

import javax.validation.Valid;
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
    public ResponseEntity<Person> findById(@PathVariable int id) throws PersonNotFoundException {
        return ResponseEntity.ok(personService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account is not found. PLease, check requisites")));
    }

    @PostMapping("/")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Person> save(@Valid @RequestBody Person person) {
        return new ResponseEntity<>(
                personService.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Person person) {
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

    @PatchMapping("/change-username")
    public ResponseEntity<Person> changeUsername(@Valid @RequestBody Person person, String username) throws PersonNotFoundException {
        Person personToSave = personService.findById(person.getId()).get();
        personToSave.setUsername(username);
        return new ResponseEntity<>(personService.save(personToSave), HttpStatus.OK);
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
