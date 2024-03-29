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
import ru.job4j.exception.UsernameIsTakenException;
import ru.job4j.service.impl.SimplePersonService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Person> save(@Valid @RequestBody Person person) throws UsernameIsTakenException {
        if (personService.save(person).isEmpty()) {
            throw new UsernameIsTakenException("");
        }
        return new ResponseEntity<>(
                personService.save(person).get(),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Person person) throws UsernameIsTakenException {
        Optional<Person> personOptional = personService.save(person);
        if (personOptional.isEmpty()) {
            throw new UsernameIsTakenException("");
        }
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
    public ResponseEntity<Person> changePassword(@Valid @RequestBody PersonDto personDto) throws PersonNotFoundException, UsernameIsTakenException {
        Optional<Person> personOptional = personService.findByUsername(personDto.getUsername());
        if ((personOptional).isEmpty()) {
            throw new PersonNotFoundException("");
        }
        Person person = personOptional.get();
        person.setPassword(personDto.getPassword());
        Optional<Person> personToSaveOptional = personService.save(person);
        if ((personToSaveOptional).isEmpty()) {
            throw new UsernameIsTakenException("");
        }
        Person personToSave = personToSaveOptional.get();
        return new ResponseEntity<>(personToSave, HttpStatus.OK);
    }

    @ExceptionHandler(value = {PersonNotFoundException.class})
    public ResponseEntity<ErrorBody> handleNotValidStatusException(PersonNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(404)
                .body(ErrorBody.builder()
                        .statusCode(404)
                        .message("Person not exists")
                        .details(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
