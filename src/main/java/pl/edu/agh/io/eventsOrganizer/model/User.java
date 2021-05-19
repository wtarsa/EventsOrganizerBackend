package pl.edu.agh.io.eventsOrganizer.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "application_users")
@Data
public class User {

    public User() {
    }

    public User(String username, String password, Boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    private Boolean admin;
}
