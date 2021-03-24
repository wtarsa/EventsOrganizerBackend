package pl.edu.agh.io.eventsOrganizer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.io.eventsOrganizer.model.Classes;
import pl.edu.agh.io.eventsOrganizer.repository.ClassesRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClassesController {

    private ClassesRepository repository;

    public ClassesController(ClassesRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public Classes searchClasses(@PathVariable Long id) {
        return repository.findById(id).get();
    }

    @GetMapping("/all")
    public List<Classes> allClasses() {
        return repository.findAll();
    }

    @GetMapping("/bulkcreate")
    public String bulkcreate() {
        repository.save(new Classes("io", "1.38",
                        LocalDateTime.of(2021, Month.MARCH, 24, 12, 0),
                        LocalDateTime.of(2021, Month.MARCH, 24, 14, 0).plusHours(2)
                )
        );
        return "Classes has been added to database.";
    }
}
