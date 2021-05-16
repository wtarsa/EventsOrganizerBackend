package pl.edu.agh.io.eventsOrganizer.controller;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger logger = LoggerFactory.getLogger(ExcelController.class);

    public ExcelController(InstructorRepository instructorRepository, ClassesRepository classesRepository) {
        this.instructorRepository = instructorRepository;
        this.classesRepository = classesRepository;
    }

    @CrossOrigin
    @GetMapping("/export")
    public @ResponseBody
    ResponseEntity<byte[]> exportExcel(@RequestParam(value = "instructorId") Long instructorId, HttpServletRequest request) throws IOException, ParseException {
        logger.info("Received request to download excel for instructor " + instructorId);
        ResponseEntity<byte[]> respEntity;
        List<Classes> classesList = classesRepository.findClassesByInstructorId(instructorId);
        File file = ExcelExport.exportToExcel(classesList, instructorRepository.findById(instructorId));
        String fileName = file.getName();

        InputStream inputStream = new FileInputStream(file);

        byte[] out = IOUtils.toByteArray(inputStream);
        inputStream.close();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=" + fileName);
        responseHeaders.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet\n");
        respEntity = new ResponseEntity<>(out, responseHeaders, HttpStatus.OK);
        logger.info("Excel file created successfully");
        if (file.delete())
            logger.info("File " + fileName + " deleted");
        else
            logger.error("File " + fileName + " not deleted");
        return respEntity;
    }
}
