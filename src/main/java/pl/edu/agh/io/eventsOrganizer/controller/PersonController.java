package pl.edu.agh.io.eventsOrganizer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.io.eventsOrganizer.model.Person;
import pl.edu.agh.io.eventsOrganizer.repository.PersonRepository;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository repository;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public Person searchPerson(@PathVariable long id) {
        return repository.getOne(id);
    }

    @GetMapping("/all")
    public List<Person> searchAllPersons() {
        return Collections.unmodifiableList(repository.findAll());
    }

    @GetMapping("/bulkcreate")
    public String bulkcreate() {
        repository.save(new Person("Adam", "Nowak", "anowak@student.agh.edu.pl"));
        return "Person has been created";
    }
}
