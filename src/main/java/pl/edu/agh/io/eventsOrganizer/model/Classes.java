package pl.edu.agh.io.eventsOrganizer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "classes")
@Data
public class Classes {

    public Classes(){
    }

    public Classes(String name, String classroom) {
        this.name = name;
        this.date = LocalDateTime.now(); // do zmiany koniecznie! ale teraz nie chce mi sie pisac parsera
        this.classroom = classroom;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private LocalDateTime date;

    private String classroom;

    @ManyToOne
    private Instructor instructor;
}
