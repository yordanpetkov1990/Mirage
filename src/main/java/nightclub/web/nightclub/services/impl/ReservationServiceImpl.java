package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.Reservation;
import nightclub.web.nightclub.entities.StatusEnum;
import nightclub.web.nightclub.entities.TableEntity;
import nightclub.web.nightclub.entities.UserDetails.UserDetailsEntity;
import nightclub.web.nightclub.entities.dtos.EditReservationDTO;
import nightclub.web.nightclub.entities.dtos.ReservationDTO;
import nightclub.web.nightclub.entities.dtos.ShowReservationDTO;
import nightclub.web.nightclub.repository.ReservationRepository;
import nightclub.web.nightclub.services.EventService;
import nightclub.web.nightclub.services.ReservationService;
import nightclub.web.nightclub.services.TableService;
import nightclub.web.nightclub.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final EventService eventService;
    private final UserService userService;

    private final TableService tableService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, EventService eventService, UserService userService, TableService tableService) {
        this.reservationRepository = reservationRepository;
        this.eventService = eventService;
        this.userService = userService;
        this.tableService = tableService;
    }

    @Override
    public void makeReservation(ReservationDTO reservationDTO, @AuthenticationPrincipal UserDetailsEntity userDetailsEntity) {
        Reservation reservation = new Reservation();
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setEvent(eventService.getEventById(reservationDTO.getEventId()).get());
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
                showReservationDTO.setId(reservation.getId());
                showReservationDTO.setDate(reservation.getEvent().getDate());
                showReservationDTO.setEventName(reservation.getEvent().getName());
                showReservationDTO.setNumberOfGuests(reservation.getNumberOfPeople());
                showReservationDTO.setStatus(reservation.getStatus().toString());
                return showReservationDTO;
            }).collect(Collectors.toList());
        }
      return   this.reservationRepository.findByOwnerIdAndStatus(id, StatusEnum.valueOf(status.toUpperCase())).stream().map(reservation -> {
            ShowReservationDTO showReservationDTO = new ShowReservationDTO();
            showReservationDTO.setId(reservation.getId());
            showReservationDTO.setDate(reservation.getEvent().getDate());
            showReservationDTO.setEventName(reservation.getEvent().getName());
            showReservationDTO.setNumberOfGuests(reservation.getNumberOfPeople());
            showReservationDTO.setStatus(reservation.getStatus().toString());
            return showReservationDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findAll() {
        return this.reservationRepository.findAll();
    }

    @Override
    public Optional<Reservation> findFirstCreatedPendingReservation() {
        return this.reservationRepository.findFirstByStatusOrderByCreatedAt(StatusEnum.PENDING);
    }

    @Override
    @Transactional
    @Modifying
    public void updateReservation(EditReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.findById(reservationDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID: "
                        + reservationDTO.getId()));
        reservation.setStatus(StatusEnum.CONFIRMED);
        Set<TableEntity> tables = new HashSet<>();

        for (Long tableId : reservationDTO.getTableIds()) {
                TableEntity tableEntity = tableService.findById(tableId).orElseThrow(() -> new IllegalArgumentException("Table not found with ID: "
                        + tableId));
                tables.add(tableEntity);
                tableEntity.setAvailable(false);

            }
        reservation.setTables(tables);

        this.reservationRepository.saveAndFlush(reservation);
    }

    @Override
    @Transactional
    @Modifying
    public void removeAllOlderThanNow() {
        this.reservationRepository.deleteAllByEvent_DateLessThan(LocalDate.now());
    }

    @Override
    public Page<Reservation> findPendingReservations(Pageable pageable) {
        return this.reservationRepository.findByStatus(StatusEnum.PENDING, pageable);
    }

    @Override
    public Page<Reservation> findReservationsByEventAndStatus(Long eventId, Pageable pageable) {
        return this.reservationRepository.findByStatusAndEvent_Id(StatusEnum.PENDING,eventId,pageable);
    }

    @Override
    @Transactional
    @Modifying
    public void cancelReservation(Long reservationId) {
        this.reservationRepository.deleteById(reservationId);
    }

}
