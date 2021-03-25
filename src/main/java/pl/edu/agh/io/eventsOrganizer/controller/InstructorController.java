package pl.edu.agh.io.eventsOrganizer.controller;

import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.eventsOrganizer.model.Instructor;
import pl.edu.agh.io.eventsOrganizer.model.Person;
import pl.edu.agh.io.eventsOrganizer.repository.InstructorRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final InstructorRepository instructorRepository;

    public InstructorController(InstructorRepository repository) {
        this.instructorRepository = repository;
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public Person searchInstructor(@PathVariable long id) {
        Optional<Instructor> instructor = instructorRepository.findById(id);
        if (instructor.isPresent()) return instructor.get();
        else throw new IllegalArgumentException("Wrong Id provided");
    }

    @CrossOrigin
    @GetMapping("/all")
    public List<Instructor> searchAllInstructors() {
        return instructorRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/bulkcreate")
    public String bulkCreate() {
        instructorRepository.save(new Instructor("Adam", "Nowak", "anowak@student.agh.edu.pl"));
        return "Instructor has been created";
    }

    @CrossOrigin
    @GetMapping("")
    public List<Instructor> searchInstructorBy(
            @RequestParam(value = "firstName", required = false) Optional<String> firstName,
            @RequestParam(value = "lastName", required = false) Optional<String> lastName,
            @RequestParam(value = "id", required = false) Optional<Long> id
    ) {
        if (id.isPresent()) {
            Optional<Instructor> instructor = instructorRepository.findById(id.get());
            if (instructor.isPresent()) {
                return List.of(instructor.get());
            } else throw new IllegalArgumentException("Id is incorrect");
        } else if (firstName.isPresent() && lastName.isPresent()) {
            return instructorRepository.findInstructorByFirstAndLastName(firstName.get(), lastName.get());
        } else {
            throw new IllegalArgumentException("Invalid Arguments");
        }
    }
}
