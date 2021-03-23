package pl.edu.agh.io.eventsOrganizer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.io.eventsOrganizer.model.Instructor;
import pl.edu.agh.io.eventsOrganizer.model.Person;
import pl.edu.agh.io.eventsOrganizer.repository.InstructorRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final InstructorRepository instructorRepository;

    public InstructorController(InstructorRepository repository) {
        this.instructorRepository = repository;
    }

    @GetMapping("/{id}")
    public Person searchInstructor(@PathVariable long id) {
        return instructorRepository.findById(id).get();
    }

    @GetMapping("/all")
    public List<Instructor> searchAllInstructors()  {
        return instructorRepository.findAll();
    }

    @GetMapping("/bulkcreate")
    public String bulkcreate() {
        instructorRepository.save(new Instructor("Adam", "Nowak", "anowak@student.agh.edu.pl"));
        return "Instructor has been created";
    }
}
