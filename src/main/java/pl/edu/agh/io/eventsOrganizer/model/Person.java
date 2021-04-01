package pl.edu.agh.io.eventsOrganizer.model;

import lombok.Data;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Data
public class Person {

    public Person() {
    }

    protected Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    protected String firstName;

    protected String lastName;

    protected String email;
}