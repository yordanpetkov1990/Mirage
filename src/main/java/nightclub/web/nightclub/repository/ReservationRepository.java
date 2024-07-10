package nightclub.web.nightclub.repository;

import nightclub.web.nightclub.entities.Reservation;
import nightclub.web.nightclub.entities.StatusEnum;
import nightclub.web.nightclub.entities.User;
import nightclub.web.nightclub.entities.dtos.ShowReservationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByOwnerIdAndStatus(Long owner_id, StatusEnum status);

    List<Reservation> findByOwnerId(Long id);
}
