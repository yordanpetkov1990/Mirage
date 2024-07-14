package nightclub.web.nightclub.services;

import nightclub.web.nightclub.entities.Reservation;
import nightclub.web.nightclub.entities.UserDetails.UserDetailsEntity;
import nightclub.web.nightclub.entities.dtos.EditReservationDTO;
import nightclub.web.nightclub.entities.dtos.ReservationDTO;
import nightclub.web.nightclub.entities.dtos.ShowReservationDTO;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    void makeReservation(ReservationDTO reservationDTO, UserDetailsEntity userDetailsEntity);

    List<ShowReservationDTO> getReservationsByUserAndStatus(Long id, String status);

    List<Reservation> findAll();

    Optional<Reservation> findFirstCreatedPendingReservation();

    void updateReservation(EditReservationDTO reservationDTO);
}
