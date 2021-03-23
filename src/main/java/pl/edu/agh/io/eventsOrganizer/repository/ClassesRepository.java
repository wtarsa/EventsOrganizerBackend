package pl.edu.agh.io.eventsOrganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.io.eventsOrganizer.model.Classes;

public interface ClassesRepository extends JpaRepository<Classes, Long> {
}
