package pl.edu.agh.io.eventsOrganizer.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "classes")
@Data
public class Classes {

    public Classes() {
    }

    public Classes(String name, String classroom) {
        this.name = name;
        this.startTime = LocalDateTime.now(); // do zmiany koniecznie! ale teraz nie chce mi sie pisac parsera
        this.endTime = LocalDateTime.now(); // do zmiany koniecznie! ale teraz nie chce mi sie pisac parsera
        this.classroom = classroom;
    }

    public Classes(String name, String classroom, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroom = classroom;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String classroom;

    @ManyToOne
    private Instructor instructor;
}
