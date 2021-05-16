package pl.edu.agh.io.eventsOrganizer.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.eventsOrganizer.excel.ExcelExport;
import pl.edu.agh.io.eventsOrganizer.model.Classes;
import pl.edu.agh.io.eventsOrganizer.repository.ClassesRepository;
import pl.edu.agh.io.eventsOrganizer.repository.InstructorRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {
    private final InstructorRepository instructorRepository;

    private final ClassesRepository classesRepository;

    public ExcelController(InstructorRepository instructorRepository, ClassesRepository classesRepository) {
        this.instructorRepository = instructorRepository;
        this.classesRepository = classesRepository;
    }


    @CrossOrigin
    @GetMapping("/export")
    public @ResponseBody
    ResponseEntity<File> exportExcel(@RequestParam(value = "instructorId") Long instructorId, HttpServletRequest request) throws IOException, ParseException {
        ResponseEntity<File> respEntity = null;
        List<Classes> classesList = classesRepository.findClassesByInstructorId(instructorId);
        ExcelExport.exportToExcel(classesList, instructorRepository.findById(instructorId));

        return respEntity;
    }

}
