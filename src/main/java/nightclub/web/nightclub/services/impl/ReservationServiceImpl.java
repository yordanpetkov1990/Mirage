package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.Reservation;
import nightclub.web.nightclub.entities.StatusEnum;
import nightclub.web.nightclub.entities.UserDetails.UserDetailsEntity;
import nightclub.web.nightclub.entities.dtos.ReservationDTO;
import nightclub.web.nightclub.entities.dtos.ShowReservationDTO;
import nightclub.web.nightclub.repository.ReservationRepository;
import nightclub.web.nightclub.services.EventService;
import nightclub.web.nightclub.services.ReservationService;
import nightclub.web.nightclub.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final EventService eventService;
    private final UserService userService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, EventService eventService, UserService userService) {
        this.reservationRepository = reservationRepository;
        this.eventService = eventService;
        this.userService = userService;
    }

    @Override
    public void makeReservation(ReservationDTO reservationDTO, @AuthenticationPrincipal UserDetailsEntity userDetailsEntity) {
        Reservation reservation = new Reservation();
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setEvent(eventService.getEventById(reservationDTO.getEventId()));
        reservation.setStatus(StatusEnum.PENDING);
        reservation.setNumberOfPeople(reservationDTO.getGuests());
        reservation.setOwner(userService.findById(userDetailsEntity.getId()));

        this.reservationRepository.save(reservation);

    }

    @Override
    public List<ShowReservationDTO> getReservationsByUserAndStatus(Long id, String status) {

        if(status == null || status.isEmpty()){
            return this.reservationRepository.findByOwnerId(id).stream().map(reservation -> {
                ShowReservationDTO showReservationDTO = new ShowReservationDTO();
                showReservationDTO.setDate(reservation.getEvent().getDate());
                showReservationDTO.setEventName(reservation.getEvent().getName());
                showReservationDTO.setNumberOfGuests(reservation.getNumberOfPeople());
                showReservationDTO.setStatus(reservation.getStatus().toString());
                return showReservationDTO;
            }).collect(Collectors.toList());
        }
      return   this.reservationRepository.findByOwnerIdAndStatus(id, StatusEnum.valueOf(status.toUpperCase())).stream().map(reservation -> {
            ShowReservationDTO showReservationDTO = new ShowReservationDTO();
            showReservationDTO.setDate(reservation.getEvent().getDate());
            showReservationDTO.setEventName(reservation.getEvent().getName());
            showReservationDTO.setNumberOfGuests(reservation.getNumberOfPeople());
            showReservationDTO.setStatus(reservation.getStatus().toString());
            return showReservationDTO;
        }).collect(Collectors.toList());
    }
}
