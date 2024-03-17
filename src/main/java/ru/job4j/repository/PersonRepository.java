package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.domain.Person;
import ru.job4j.exception.PersonNotFoundException;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findById(int id) throws PersonNotFoundException;
}
