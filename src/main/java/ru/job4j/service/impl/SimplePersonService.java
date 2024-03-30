package ru.job4j.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Person;
import ru.job4j.exception.PersonNotFoundException;
import ru.job4j.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimplePersonService  implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByUsername(username);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        Person user = person.get();
        return new User(user.getUsername(), user.getPassword(), emptyList());
    }
}
