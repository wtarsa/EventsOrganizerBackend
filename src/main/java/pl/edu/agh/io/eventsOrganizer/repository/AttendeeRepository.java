package pl.edu.agh.io.eventsOrganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.io.eventsOrganizer.model.Attendee;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
}
