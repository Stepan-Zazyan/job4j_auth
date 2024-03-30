package ru.job4j.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Person;
import ru.job4j.exception.PersonNotFoundException;
import ru.job4j.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimplePersonService {

    private final PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(int id) throws PersonNotFoundException {
        return personRepository.findById(id);
    }

    public Optional<Person> save(Person person) {
        try {
            personRepository.save(person);
            return Optional.of(person);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    public void update(Person person) throws DataAccessException {
        personRepository.save(person);
    }

    public void delete(Person person) throws DataAccessException {
        personRepository.delete(person);
    }

    /**
     * @param name String
     * @return Person object
     * @throws PersonNotFoundException custom
     */
    public Optional<Person> findByUsername(String name) throws PersonNotFoundException {
        Optional<Person> person = personRepository.findByUsername(name);
        if (person.isEmpty()) {
            throw new PersonNotFoundException("Пользователя с таким логином не существует");
        }
        return personRepository.findByUsername(name);
    }

}
