package pl.edu.agh.io.eventsOrganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.io.eventsOrganizer.model.Instructor;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    @Query("select i from Instructor i where i.firstName = :firstName and i.lastName = :lastName")
    List<Instructor> findInstructorByFirstAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
