package pl.edu.agh.io.eventsOrganizer.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.eventsOrganizer.errors.NotFoundException;
import pl.edu.agh.io.eventsOrganizer.forms.ClassesSubmitForm;
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
import java.util.UUID;
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
    public ResponseEntity<Classes> searchClasses(@PathVariable UUID id, HttpServletRequest request) {
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
    @PostMapping("/submit")
    public ResponseEntity<Classes> addClassesByForm(
            @RequestBody ClassesSubmitForm classesSubmitForm,
            HttpServletRequest request
    ) {
        List<Instructor> instructors = instructorRepository
                .findInstructorByFirstNameAndLastName(classesSubmitForm.getFirstName(), classesSubmitForm.getLastName());

        Instructor savedInstructor;
        if (instructors.size() == 0) { // Adding dummy instructor
            Instructor dummyInstructor
                    = new Instructor(classesSubmitForm.getFirstName(), classesSubmitForm.getLastName(), null);
            savedInstructor = instructorRepository.save(dummyInstructor);

        } else { // Silent Assumption - unique firstName and lastName
            savedInstructor = instructors.get(0);
        }

        Classes newClasses = classesSubmitForm.getClassesWithInstructor(savedInstructor);

        return new ResponseEntity<>(repository.save(newClasses), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/{id}/addInstructor")
    public ResponseEntity<Classes> addInstructor(
            @RequestBody Instructor instructor,
            @PathVariable UUID id,
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
        editedClasses.setInstructor(instructor);
        updateClasses(editedClasses.toClassesSubmitForm(), editedClasses.getId());
        return new ResponseEntity<>(editedClasses, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClasses(@PathVariable UUID id, HttpServletRequest request) {
        repository.deleteById(id);
        return new ResponseEntity<>("{\"Status\": \"Classes with id " + id + " has been deleted.\"}", HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public Classes updateClasses(@RequestBody ClassesSubmitForm newClasses, @PathVariable UUID id) {
        List<Instructor> instructorList = instructorRepository
                .findInstructorByFirstNameAndLastName(newClasses.getFirstName(), newClasses.getLastName());
        Instructor instructor = instructorList.size() == 0 ? new Instructor(newClasses.getFirstName(),
                newClasses.getLastName(), "") : instructorList.get(0);
        instructorRepository.save(instructor);

        return repository.findById(id)
                .map(classes -> {
                    classes.setAppointmentNumber(newClasses.getAppointmentNumber());
                    classes.setStartTime(LocalDateTime.parse(newClasses.getStartTime(), Classes.formatter));
                    classes.setEndTime(LocalDateTime.parse(newClasses.getEndTime(), Classes.formatter));
                    classes.setName(newClasses.getName());
                    classes.setStudentsGroup(newClasses.getStudentsGroup());
                    classes.setInstructor(instructor);
                    classes.setClassesType(newClasses.getClassesType());
                    classes.setNumberOfHours(newClasses.getNumberOfHours());
                    classes.setClassesForm(newClasses.getClassesForm());
                    classes.setClassroom(newClasses.getClassroom());
                    return repository.save(classes);
                })
                .orElseGet(() -> {
                    Classes classes = new Classes(newClasses.getAppointmentNumber(), newClasses.getStartTime(), newClasses.getEndTime(),
                            newClasses.getName(), newClasses.getStudentsGroup(), instructor, newClasses.getClassesType(),
                            newClasses.getNumberOfHours(), newClasses.getClassesForm(), newClasses.getClassroom(), newClasses.getEvent());
                    classes.setId(id);
                    return repository.save(classes);
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
                "WSZYSCY", instructor, ClassesType.LECTURE, 2, ClassesForm.REMOTE, "1.38", "SFI"));
        return new ResponseEntity<>("{\"Status\": \"Classes has been added to database.\"}", HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/where")
    public ResponseEntity<List<Classes>> searchClassesBy(
            @RequestParam(value = "firstName", required = false) Optional<String> firstName,
            @RequestParam(value = "lastName", required = false) Optional<String> lastName,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> endDate,
            @RequestParam(value = "classroom", required = false) Optional<String> classroom,
            @RequestParam(value = "event", required = false) Optional<String> event,
            HttpServletRequest request
    ) {
        List<Classes> classes = repository.findAll();
        if (firstName.isPresent())
            classes = classes.stream().filter(a -> a.getInstructor().getFirstName().equals(firstName.get())).collect(Collectors.toList());
        if (lastName.isPresent())
            classes = classes.stream().filter(a -> a.getInstructor().getLastName().equals(lastName.get())).collect(Collectors.toList());
        if (startDate.isPresent())
            classes = classes.stream().filter(a -> a.getStartTime().isAfter(startDate.get())).collect(Collectors.toList());
        if (endDate.isPresent())
            classes = classes.stream().filter(a -> a.getEndTime().isBefore(endDate.get())).collect(Collectors.toList());
        if (classroom.isPresent())
            classes = classes.stream().filter(a -> a.getClassroom() != null && a.getClassroom().equals(classroom.get())).collect(Collectors.toList());
        if (event.isPresent())
            classes = classes.stream().filter(a -> a.getEvent() != null && a.getEvent().equals(event.get())).collect(Collectors.toList());

        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/events/all")
    public ResponseEntity<List<String>> getAllEvents(HttpServletRequest request) {
        return new ResponseEntity<>(repository.findAll().stream()
                .map(c -> c.getEvent())
                .distinct()
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }
}
