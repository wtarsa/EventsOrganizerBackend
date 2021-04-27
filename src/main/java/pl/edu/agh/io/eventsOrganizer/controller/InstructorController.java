package pl.edu.agh.io.eventsOrganizer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import pl.edu.agh.io.eventsOrganizer.errors.NotFoundException;
import pl.edu.agh.io.eventsOrganizer.model.Classes;
import pl.edu.agh.io.eventsOrganizer.model.Instructor;
import pl.edu.agh.io.eventsOrganizer.model.Person;
import pl.edu.agh.io.eventsOrganizer.repository.ClassesRepository;
import pl.edu.agh.io.eventsOrganizer.repository.InstructorRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final InstructorRepository instructorRepository;

    private final ClassesRepository classesRepository;

    public InstructorController(InstructorRepository instructorRepository, ClassesRepository classesRepository) {
        this.instructorRepository = instructorRepository;
        this.classesRepository = classesRepository;
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Person> searchInstructor(@PathVariable long id, HttpServletRequest request) {
        Optional<Instructor> instructor = instructorRepository.findById(id);
        if (instructor.isPresent())
            return new ResponseEntity<>(instructor.get(), HttpStatus.OK);
        else
            throw new NotFoundException(
                    "Instructor with provided id not found",
                    request.getRequestURI(),
                    List.of("Instructor " + id + " not found")
            );
    }

    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<List<Instructor>> searchAllInstructors(HttpServletRequest request) {
        return new ResponseEntity<>(instructorRepository.findAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/bulkcreate")
    public ResponseEntity<String> bulkCreate(HttpServletRequest request) {
        instructorRepository.save(new Instructor("Adam", "Nowak", "anowak@student.agh.edu.pl"));
        return new ResponseEntity<>("{\"Status\": \"Instructor has been added to database.\"}", HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Instructor> addInstructor(@RequestBody Instructor newInstructor, HttpServletRequest request) {
        return new ResponseEntity<>(instructorRepository.save(newInstructor), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInstructor(@PathVariable Long id, HttpServletRequest request) {
        instructorRepository.deleteById(id);
        return new ResponseEntity<>("{\"Status\": \"Instructor with id " + id + " has been deleted.\"}", HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Instructor> updateInstructor(
            @RequestBody Instructor newInstructor,
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(instructorRepository.findById(id)
                .map(instructor -> {
                    instructor.setFirstName(newInstructor.getFirstName());
                    instructor.setLastName(newInstructor.getLastName());
                    instructor.setEmail(newInstructor.getEmail());
                    return instructorRepository.save(instructor);
                })
                .orElseGet(() -> {
                    newInstructor.setId(id);
                    return instructorRepository.save(newInstructor);
                }),
                HttpStatus.OK
        );
    }

    @CrossOrigin
    @GetMapping("/where")
    public ResponseEntity<List<Instructor>> searchInstructorBy(
            @RequestParam(value = "firstName", required = false) Optional<String> firstName,
            @RequestParam(value = "lastName", required = false) Optional<String> lastName,
            @RequestParam(value = "id", required = false) Optional<Long> id,
            HttpServletRequest request
    ) {
        if (id.isPresent()) {
            Optional<Instructor> instructor = instructorRepository.findById(id.get());
            if (instructor.isPresent()) return new ResponseEntity<>(List.of(instructor.get()), HttpStatus.OK);
            else throw new NotFoundException(
                    "Instructor with id: " + id + " not found",
                    HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE,
                    List.of("Instructor with id: " + id + " not found"));
        } else if (firstName.isPresent() && lastName.isPresent()) {
            return new ResponseEntity<>(instructorRepository
                    .findInstructorByFirstNameAndLastName(firstName.get(), lastName.get()), HttpStatus.OK);
        } else if (firstName.isPresent()) {
            return new ResponseEntity<>(instructorRepository
                    .findByFirstName(firstName.get()), HttpStatus.OK);
        } else if (lastName.isPresent()) {
            return new ResponseEntity<>(instructorRepository
                    .findByLastName(lastName.get()), HttpStatus.OK);

        } else {
            throw new NotFoundException(
                    "The request has no arguments given",
                    request.getRequestURI(),
                    List.of("The request has no arguments given")
            );
        }
    }

    @CrossOrigin
    @GetMapping("/{id}/classes")
    public ResponseEntity<List<Classes>> findClassesConductedByInstructor(@PathVariable Long id) {
        return new ResponseEntity<>(classesRepository.findClassesByInstructorId(id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}/timetable")
    public ResponseEntity<List<Classes>> findFutureInstructorClasses(@PathVariable Long id) {
        List<Classes> futureClasses = classesRepository.findClassesByInstructorId(id).stream()
                .filter(classes -> classes.getStartTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(futureClasses, HttpStatus.OK);
    }
}
