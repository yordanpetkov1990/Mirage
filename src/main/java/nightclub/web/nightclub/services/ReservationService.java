package nightclub.web.nightclub.services;

import nightclub.web.nightclub.entities.Reservation;
import nightclub.web.nightclub.entities.UserDetails.UserDetailsEntity;
import nightclub.web.nightclub.entities.dtos.EditReservationDTO;
import nightclub.web.nightclub.entities.dtos.ReservationDTO;
import nightclub.web.nightclub.entities.dtos.ShowReservationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

    void makeReservation(ReservationDTO reservationDTO, UserDetailsEntity userDetailsEntity);

    List<ShowReservationDTO> getReservationsByUserAndStatus(Long id, String status);

    List<Reservation> findAll();

    Optional<Reservation> findFirstCreatedPendingReservation();

    void updateReservation(EditReservationDTO reservationDTO);

    void removeAllOlderThanNow();

    Page<Reservation> findPendingReservations(Pageable pageable);

    Page<Reservation> findReservationsByEventAndStatus(Long eventId, Pageable pageable);

    void cancelReservation(Long reservationId);
}
