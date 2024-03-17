package ru.job4j.mapper;

import ru.job4j.domain.Person;
import ru.job4j.dto.PersonDto;

public class mapperPerson {

    public PersonDto personToDto(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setUsername(person.getUsername());
        personDto.setPassword(person.getPassword());
        return personDto;
    }

    public Person personDtoToPerson(PersonDto personDto) {
        Person person = new Person();
        person.setUsername(personDto.getUsername());
        person.setPassword(personDto.getPassword());
        return person;
    }
}
