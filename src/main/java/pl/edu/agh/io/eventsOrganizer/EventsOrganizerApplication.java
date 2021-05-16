package pl.edu.agh.io.eventsOrganizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.agh.io.eventsOrganizer.excel.ExcelExport;
import pl.edu.agh.io.eventsOrganizer.model.Classes;
import pl.edu.agh.io.eventsOrganizer.model.ClassesForm;
import pl.edu.agh.io.eventsOrganizer.model.ClassesType;
import pl.edu.agh.io.eventsOrganizer.model.Instructor;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class EventsOrganizerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(EventsOrganizerApplication.class, args);
//        ExcelExport.exportToExcel(List.of(new Classes(1, "2021-07-03 08.00", "2021-07-03 08.00", "Classes Name", "studentsGroup",
//                new Instructor("przes", "asd", "dsa"), ClassesType.LABORATORY, 1, ClassesForm.REMOTE, " classroom", " event")));

    }
}
