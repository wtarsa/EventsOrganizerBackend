package pl.edu.agh.io.eventsOrganizer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.eventsOrganizer.generator.Generator;
import pl.edu.agh.io.eventsOrganizer.generator.GeneratorStructure;
import pl.edu.agh.io.eventsOrganizer.model.Classes;
import pl.edu.agh.io.eventsOrganizer.model.Instructor;
import pl.edu.agh.io.eventsOrganizer.repository.ClassesRepository;
import pl.edu.agh.io.eventsOrganizer.repository.InstructorRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/generator")
public class GeneratorController {
    private final ClassesRepository classesRepository;
    private final InstructorRepository instructorRepository;
    private Generator generator = new Generator();

    public GeneratorController(ClassesRepository classesRepository, InstructorRepository instructorRepository) {
        this.classesRepository = classesRepository;
        this.instructorRepository = instructorRepository;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<List<Classes>> generate(@RequestBody GeneratorStructure structure, HttpServletRequest request) {
        List<Instructor> instructors = generator.generateInstructors(structure.getInstructorsCount());
        List<String> events = generator.generateEvents(structure.getEventsCount());
        List<Classes> classes = generator.generateClasses(instructors, events, structure.getClassesCount());

        for (Instructor instructor : instructors)
            instructorRepository.save(instructor);
        for (Classes classes1 : classes)
            classesRepository.save(classes1);

        return new ResponseEntity<>(classes, HttpStatus.OK);
    }
}
