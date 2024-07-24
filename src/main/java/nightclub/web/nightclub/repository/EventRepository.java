package nightclub.web.nightclub.repository;

import nightclub.web.nightclub.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    void deleteAllByDateLessThan(LocalDate date);
}
