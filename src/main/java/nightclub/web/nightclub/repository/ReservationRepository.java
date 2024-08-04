package nightclub.web.nightclub.repository;

import nightclub.web.nightclub.entities.Event;
import nightclub.web.nightclub.entities.Reservation;
import nightclub.web.nightclub.entities.StatusEnum;
import nightclub.web.nightclub.entities.User;
import nightclub.web.nightclub.entities.dtos.ShowReservationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByOwnerIdAndStatus(Long owner_id, StatusEnum status);

    List<Reservation> findByOwnerId(Long id);

    Optional<Reservation> findFirstByStatusOrderByCreatedAt(StatusEnum status);
    void deleteAllByEvent_DateLessThan(LocalDate event_date);

    Page<Reservation> findByStatus(StatusEnum statusEnum, Pageable pageable);

    Page<Reservation> findByStatusAndEvent_Id(StatusEnum status, Long event_id, Pageable pageable);
}
