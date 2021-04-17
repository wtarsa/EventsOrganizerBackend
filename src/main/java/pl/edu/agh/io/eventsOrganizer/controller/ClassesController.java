package pl.edu.agh.io.eventsOrganizer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.eventsOrganizer.errors.NotFoundException;
import pl.edu.agh.io.eventsOrganizer.model.Classes;
import pl.edu.agh.io.eventsOrganizer.model.ClassesForm;
import pl.edu.agh.io.eventsOrganizer.model.ClassesType;
import pl.edu.agh.io.eventsOrganizer.model.Instructor;
import pl.edu.agh.io.eventsOrganizer.repository.ClassesRepository;
import pl.edu.agh.io.eventsOrganizer.repository.InstructorRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<Classes> searchClasses(@PathVariable Long id, HttpServletRequest request) {
        Optional<Classes> course = repository.findById(id);
        if (course.isPresent())
            return new ResponseEntity<>(course.get(), HttpStatus.OK);
        else
            throw new NotFoundException(
                    "Class with provided id not found",
                    request.getRequestURI(),
                    List.of("Class " + id + " not found")
            );
    }

    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<List<Classes>> allClasses(HttpServletRequest request) {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Classes> addClasses(@RequestBody Classes newClasses, HttpServletRequest request) {
        return new ResponseEntity<>(repository.save(newClasses), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/{id}/addInstructor")
    public ResponseEntity<Classes> addInstructor(
            @RequestBody Instructor instructor,
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        Classes editedClasses = repository.findById(id).orElse(null);
        if (editedClasses == null) {
            throw new NotFoundException(
                    "Classes with provided id: " + id + " not found.",
                    request.getRequestURI(),
                    List.of("Classes with provided id: " + id + " not found.")
            );
        }
//        instructor.addClasses(editedClasses);
        editedClasses.setInstructor(instructor);
        updateClasses(editedClasses, editedClasses.getId());
        return new ResponseEntity<>(editedClasses, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClasses(@PathVariable Long id, HttpServletRequest request) {
        repository.deleteById(id);
        return new ResponseEntity<>("{\"Status\": \"Classes with id " + id + " has been deleted.\"}", HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public Classes updateClasses(@RequestBody Classes newClasses, @PathVariable Long id) {
        return repository.findById(id)
                .map(classes -> {
                    classes.setAppointmentNumber(newClasses.getAppointmentNumber());
                    classes.setStartTime(newClasses.getStartTime());
                    classes.setEndTime(newClasses.getEndTime());
                    classes.setName(newClasses.getName());
                    classes.setStudentsGroup(newClasses.getStudentsGroup());
                    classes.setInstructor(newClasses.getInstructor());
                    classes.setClassesType(newClasses.getClassesType());
                    classes.setNumberOfHours(newClasses.getNumberOfHours());
                    classes.setClassesForm(newClasses.getClassesForm());
                    classes.setClassroom(newClasses.getClassroom());
                    return repository.save(classes);
                })
                .orElseGet(() -> {
                    newClasses.setId(id);
                    return repository.save(newClasses);
                });
    }

    @CrossOrigin
    @GetMapping("/bulkCreate")
    public ResponseEntity<String> bulkCreate(HttpServletRequest request) {
        Instructor instructor = instructorRepository.findById(1L).orElse(null);
        if (instructor == null) {
            throw new NotFoundException(
                    "Instructor with provided id: 1 not found.",
                    request.getRequestURI(),
                    List.of("Instructor with provided id: 1 not found.")
            );
        }
        repository.save(new Classes(1, "2021-03-30 08.00", "2021-03-30 09.30", "IO",
                "WSZYSCY", instructor, ClassesType.LECTURE, 2, ClassesForm.REMOTE, "1.38"));
        return new ResponseEntity<>("{\"Status\": \"Classes has been added to database.\"}", HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/where")
    public ResponseEntity<List<Classes>> searchClassesBy(
            @RequestParam(value = "firstName", required = false) Optional<String> firstName,
            @RequestParam(value = "lastName", required = false) Optional<String> lastName,
            @RequestParam(value = "startDate", required = false) Optional<LocalDateTime> startDate,
            @RequestParam(value = "endDate", required = false) Optional<LocalDateTime> endDate,
            @RequestParam(value = "classroom", required = false) Optional<String> classroom,
            HttpServletRequest request
    ) {
        List<Classes> classes = repository.findAll();
        if (firstName.isPresent())
            classes = classes.stream().filter(a -> a.getInstructor().getFirstName().equals(firstName.get())).collect(Collectors.toList());
        if (lastName.isPresent())
            classes = classes.stream().filter(a -> a.getInstructor().getLastName().equals(lastName.get())).collect(Collectors.toList());
        if(startDate.isPresent())
            classes = classes.stream().filter(a -> a.getStartTime().isAfter(startDate.get())).collect(Collectors.toList());
        if(endDate.isPresent())
            classes = classes.stream().filter(a -> a.getEndTime().isBefore(endDate.get())).collect(Collectors.toList());
        if(classroom.isPresent())
            classes = classes.stream().filter(a -> a.getClassroom().equals(classroom.get())).collect(Collectors.toList());

        return new ResponseEntity<>(classes, HttpStatus.OK);
    }
}
