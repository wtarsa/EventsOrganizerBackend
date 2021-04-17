package pl.edu.agh.io.eventsOrganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.io.eventsOrganizer.model.Instructor;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    List<Instructor> findInstructorByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    List<Instructor> findByFirstName(String firstName);

    List<Instructor> findByLastName(@Param("lastName") String lastName);
}
