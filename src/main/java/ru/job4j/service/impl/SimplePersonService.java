package ru.job4j.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Person;
import ru.job4j.exception.PersonNotFoundException;
import ru.job4j.repository.PersonRepository;
import ru.job4j.service.PersonService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimplePersonService {

    private  final PersonRepository personRepository;

    public List<Person> findAll() {
       return personRepository.findAll();
    }

    public Optional<Person> findById(int id) throws PersonNotFoundException {
        return personRepository.findById(id);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void update(Person person) throws DataAccessException {
       personRepository.save(person);
    }

    public void delete(Person person) throws DataAccessException {
        personRepository.delete(person);
    }

    public Optional<Person> findByUsername(String name) throws PersonNotFoundException {
        return personRepository.findByUsername(name);
    }
}
