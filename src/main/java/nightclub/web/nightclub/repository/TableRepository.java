package nightclub.web.nightclub.repository;

import nightclub.web.nightclub.entities.Reservation;
import nightclub.web.nightclub.entities.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TableRepository extends JpaRepository<TableEntity,Long> {
    @Query("SELECT t FROM TableEntity t WHERE t.id NOT IN " +
            "(SELECT rt.id FROM Reservation r JOIN r.tables rt WHERE r.event.id = :eventId)")
    List<TableEntity> findAvailableTablesForEvent(@Param("eventId") Long eventId);


}
