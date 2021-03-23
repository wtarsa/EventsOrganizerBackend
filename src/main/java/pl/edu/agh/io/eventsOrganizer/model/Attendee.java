package pl.edu.agh.io.eventsOrganizer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class Attendee extends Person {

    public Attendee() {
        super();
    }

    public Attendee(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    @ManyToMany
    private final List<Classes> enrolledClasses = new LinkedList<>();
}
