package pl.edu.agh.io.eventsOrganizer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class Instructor extends Person{

    public Instructor() {
        super();
    }

    public Instructor(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    @OneToMany
    private final List<Classes> conductedClasses = new LinkedList<>();
}
