package nightclub.web.nightclub.repository;

import nightclub.web.nightclub.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
