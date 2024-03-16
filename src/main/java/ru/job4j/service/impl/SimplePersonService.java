package ru.job4j.service.impl;

import lombok.RequiredArgsConstructor;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;
import ru.job4j.service.PersonService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class SimplePersonService implements PersonService {

    private  final PersonRepository personRepository;

    public List<Person> findAll() {
       return personRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void update(Person person) {
       personRepository.save(person);
    }

    public void delete(Person person) {
        personRepository.delete(person);
    }
}
