package pl.edu.agh.io.eventsOrganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.io.eventsOrganizer.model.Classes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassesRepository extends JpaRepository<Classes, UUID> {

    List<Classes> findClassesByInstructorFirstName(@Param("firstName") String firstName);

    List<Classes> findClassesByInstructorLastName(@Param("firstName") String firstName);

    List<Classes> findClassesByInstructorId(@Param("id") Long id);

    List<Classes> findClassesByClassroom(@Param("classroom") String classroom);

    List<Classes> findClassesByStartTimeAfter(@Param("startTime") LocalDateTime startTime);

    List<Classes> findClassesByEndTimeBefore(@Param("endTime") LocalDateTime endTime);
}
