package nightclub.web.nightclub.services;

import nightclub.web.nightclub.entities.UserDetails.UserDetailsEntity;
import nightclub.web.nightclub.entities.dtos.ReservationDTO;
import nightclub.web.nightclub.entities.dtos.ShowReservationDTO;

import java.util.List;

public interface ReservationService {

    void makeReservation(ReservationDTO reservationDTO, UserDetailsEntity userDetailsEntity);

    List<ShowReservationDTO> getReservationsByUserAndStatus(Long id, String status);
}
