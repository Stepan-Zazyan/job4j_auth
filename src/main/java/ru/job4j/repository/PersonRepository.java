package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.domain.Person;
import ru.job4j.exception.PersonNotFoundException;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findById(int id) throws PersonNotFoundException;

    Optional<Person> findByUsername(String name);

    boolean existsByUsername(String name);

}
