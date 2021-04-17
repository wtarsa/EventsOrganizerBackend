package pl.edu.agh.io.eventsOrganizer.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Instructor extends Person {

    public Instructor() {
        super();
    }

    public Instructor(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }
}
