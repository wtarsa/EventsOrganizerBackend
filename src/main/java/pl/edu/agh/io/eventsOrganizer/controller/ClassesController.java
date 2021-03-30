package pl.edu.agh.io.eventsOrganizer.controller;

import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.eventsOrganizer.model.Classes;
import pl.edu.agh.io.eventsOrganizer.model.ClassesForm;
import pl.edu.agh.io.eventsOrganizer.model.ClassesType;
import pl.edu.agh.io.eventsOrganizer.model.Instructor;
import pl.edu.agh.io.eventsOrganizer.repository.ClassesRepository;
import pl.edu.agh.io.eventsOrganizer.repository.InstructorRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classes")
public class ClassesController {

    private final ClassesRepository repository;

    private final InstructorRepository instructorRepository;

    public ClassesController(ClassesRepository repository, InstructorRepository instructorRepository) {
        this.repository = repository;
        this.instructorRepository = instructorRepository;
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public Classes searchClasses(@PathVariable Long id) {
        Optional<Classes> course = repository.findById(id);
        return course.orElseGet(Classes::new);
    }

    @CrossOrigin
    @GetMapping("/all")
    public List<Classes> allClasses() {
        return repository.findAll();
    }

    @CrossOrigin
    @GetMapping("/bulkcreate")
    public String bulkCreate() {
        Instructor instructor = instructorRepository.findById(1L).get();
        repository.save(new Classes(1, "2021-03-30 08.00", "2021-03-30 09.30", "IO",
                "WSZYSCY", instructor, ClassesType.LECTURE, 2, ClassesForm.REMOTE, "1.38"));
        return "Classes has been added to database.";
    }
}
