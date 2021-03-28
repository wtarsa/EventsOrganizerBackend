package pl.edu.agh.io.eventsOrganizer.controller;

import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.eventsOrganizer.model.Classes;
import pl.edu.agh.io.eventsOrganizer.repository.ClassesRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classes")
public class ClassesController {

    private final ClassesRepository repository;
    public ClassesController(ClassesRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public Classes searchClasses(@PathVariable Long id) {
        Optional<Classes> course = repository.findById(id);
        if (course.isPresent()) return course.get();
        else throw new IllegalArgumentException("Wrong Id provided");
    }

    @CrossOrigin
    @GetMapping("/all")
    public List<Classes> allClasses() {
        return repository.findAll();
    }

    @CrossOrigin
    @GetMapping("/bulkcreate")
    public String bulkCreate() {
        repository.save(new Classes("io", "1.38",
                        LocalDateTime.of(2021, Month.MARCH, 24, 12, 0),
                        LocalDateTime.of(2021, Month.MARCH, 24, 14, 0).plusHours(2)
                )
        );
        return "Classes has been added to database.";
    }
}
