package pl.edu.agh.io.eventsOrganizer.controller;

import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.eventsOrganizer.model.Classes;
import pl.edu.agh.io.eventsOrganizer.model.Instructor;
import pl.edu.agh.io.eventsOrganizer.model.Person;
import pl.edu.agh.io.eventsOrganizer.repository.InstructorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return instructor.orElseGet(Instructor::new);
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
    @PostMapping
    public Instructor addInstructor(@RequestBody Instructor newInstructor) {
        return instructorRepository.save(newInstructor);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public String deleteInstructor(@PathVariable Long id) {
        instructorRepository.deleteById(id);
        return "Instructor with id " + id + " has been deleted.";
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public Instructor updateInstructor(@RequestBody Instructor newInstructor, @PathVariable Long id) {
        return instructorRepository.findById(id)
                .map(instructor -> {
                    instructor.setFirstName(newInstructor.getFirstName());
                    instructor.setLastName(newInstructor.getLastName());
                    instructor.setEmail(newInstructor.getEmail());
//                    instructor.setConductedClasses(newInstructor.getConductedClasses().stream().
//                            map(c -> c.setInstructor(null)).collect(Collectors.toList()));
                    return instructorRepository.save(instructor);
                })
                .orElseGet(() -> {
                    newInstructor.setId(id);
                    return instructorRepository.save(newInstructor);
                });
    }

    @CrossOrigin
    @GetMapping("/where")
    public List<Instructor> searchInstructorBy(
            @RequestParam(value = "firstName", required = false) Optional<String> firstName,
            @RequestParam(value = "lastName", required = false) Optional<String> lastName,
            @RequestParam(value = "id", required = false) Optional<Long> id
    ) {
        if (id.isPresent()) {
            Optional<Instructor> instructor = instructorRepository.findById(id.get());
            return instructor.map(List::of).orElseGet(() -> List.of(new Instructor()));
        } else if (firstName.isPresent() && lastName.isPresent()) {
            return instructorRepository.findInstructorByFirstAndLastName(firstName.get(), lastName.get());
        } else if (firstName.isPresent()) {
            return instructorRepository.findInstructorByFirstName(firstName.get());
        } else if (lastName.isPresent()) {
            return instructorRepository.findInstructorByLastName(lastName.get());
        } else {
            return List.of(new Instructor());
        }
    }
}
