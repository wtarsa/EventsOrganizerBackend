package pl.edu.agh.io.eventsOrganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.io.eventsOrganizer.model.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
