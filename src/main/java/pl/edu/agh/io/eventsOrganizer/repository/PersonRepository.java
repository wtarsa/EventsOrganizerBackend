package pl.edu.agh.io.eventsOrganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.io.eventsOrganizer.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
