package pl.edu.agh.io.eventsOrganizer.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "classes")
@Data
public class Classes {

    public Classes() {
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm");

    public Classes(int appointmentNumber, String startTime, String endTime, String name, String studentsGroup,
                   Instructor instructor, ClassesType classesType, int numberOfHours, ClassesForm classesForm, String classroom, String event) {
        this.appointmentNumber = appointmentNumber;
        this.startTime = LocalDateTime.parse(startTime, formatter);
        this.endTime = LocalDateTime.parse(endTime, formatter);
        this.name = name;
        this.studentsGroup = studentsGroup;
        this.instructor = instructor;
        this.classesType = classesType;
        this.numberOfHours = numberOfHours;
        this.classesForm = classesForm;
        this.classroom = classroom;
        this.event = event;
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    private int appointmentNumber; //numer zjazdu

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String name;

    private String studentsGroup;

    @ManyToOne
    private Instructor instructor;

    private ClassesType classesType;

    private int numberOfHours;

    private ClassesForm classesForm;

    private String classroom;

    private String event;

    public String getReminderNote() {
        return "event:" + event  + "\n" +
                "name:" + name  + "\n" +
                "start time:" + startTime  + "\n" +
                "end time:" + endTime  + "\n" +
                "classroom:" + classroom  + "\n\n";
    }
}
