package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.*;
import nightclub.web.nightclub.entities.UserDetails.UserDetailsEntity;
import nightclub.web.nightclub.entities.dtos.EditReservationDTO;
import nightclub.web.nightclub.entities.dtos.ReservationDTO;
import nightclub.web.nightclub.entities.dtos.ShowReservationDTO;
import nightclub.web.nightclub.repository.ReservationRepository;
import nightclub.web.nightclub.repository.UserRepository;
import nightclub.web.nightclub.services.EventService;
import nightclub.web.nightclub.services.TableService;
import nightclub.web.nightclub.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ModelMap;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {
    private ReservationServiceImpl toTest;
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private EventService eventService;
    @Mock
    private UserService userService;
    @Mock
    private TableService tableService;

    @Captor
    private ArgumentCaptor<Reservation> reservationCaptor;

    private ModelMapper modelMapper = new ModelMapper();


    @BeforeEach
    void setUp() {
        toTest = new ReservationServiceImpl(
                reservationRepository,
                eventService,
                userService,
                tableService
        );

    }

    @Test
    void testMakeReservation() {
        // Arrange
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setEventId(1L);
        reservationDTO.setGuests(5);

        UserDetailsEntity userDetailsEntity = new UserDetailsEntity(
                "username", "password", Collections.emptyList(),
                "John", "Doe", 1L, "1234567890"
        );

        Event event = new Event();
        event.setId(1L);
        event.setDate(LocalDate.now());
        User map = modelMapper.map(userDetailsEntity, User.class);

        when(eventService.getEventById(anyLong())).thenReturn(Optional.of(event));
        when(userService.findById(anyLong())).thenReturn(map);

        // Act
        toTest.makeReservation(reservationDTO, userDetailsEntity);

        // Assert
        verify(reservationRepository).save(reservationCaptor.capture());
        Reservation reservation = reservationCaptor.getValue();
        assertEquals(StatusEnum.PENDING, reservation.getStatus());
        assertEquals(5, reservation.getNumberOfPeople());
        assertEquals(event, reservation.getEvent());
        assertEquals(map, reservation.getOwner());
    }

    @Test
    void testGetReservationsByUserAndStatus() {
        // Arrange
        Long userId = 1L;
        String status = "PENDING";

        Reservation reservation = new Reservation();
        reservation.setEvent(new Event());
        reservation.setStatus(StatusEnum.PENDING);
        reservation.setNumberOfPeople(5);

        when(reservationRepository.findByOwnerIdAndStatus(anyLong(), any(StatusEnum.class)))
                .thenReturn(Collections.singletonList(reservation));

        // Act
        List<ShowReservationDTO> reservations = toTest.getReservationsByUserAndStatus(userId, status);

        // Assert
        assertFalse(reservations.isEmpty());
        ShowReservationDTO showReservationDTO = reservations.get(0);
        assertEquals("PENDING", showReservationDTO.getStatus());
        assertEquals(5, showReservationDTO.getNumberOfGuests());
    }
    @Test
    void testFindAll() {
        // Arrange
        Reservation reservation = new Reservation();
        List<Reservation> reservations = Collections.singletonList(reservation);
        when(reservationRepository.findAll()).thenReturn(reservations);

        // Act
        List<Reservation> result = toTest.findAll();

        // Assert
        assertEquals(reservations, result);
    }
    @Test
    void testFindFirstCreatedPendingReservation() {
        // Arrange
        Reservation reservation = new Reservation();
        reservation.setStatus(StatusEnum.PENDING);
        when(reservationRepository.findFirstByStatusOrderByCreatedAt(StatusEnum.PENDING))
                .thenReturn(Optional.of(reservation));

        // Act
        Optional<Reservation> result = toTest.findFirstCreatedPendingReservation();

        // Assert
        assertTrue(result.isPresent());
        assertEquals(reservation, result.get());
    }

    @Test
    void testUpdateReservation() {
        // Arrange
        EditReservationDTO editReservationDTO = new EditReservationDTO();
        editReservationDTO.setId(1L);
        editReservationDTO.setStatus("CONFIRMED");
        editReservationDTO.setTableIds(Collections.singletonList(1L));

        Reservation reservation = new Reservation();
        reservation.setStatus(StatusEnum.PENDING);
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));

        TableEntity tableEntity = new TableEntity();
        tableEntity.setAvailable(true);
        when(tableService.findById(anyLong())).thenReturn(Optional.of(tableEntity));

        // Act
        toTest.updateReservation(editReservationDTO);

        // Assert
        verify(reservationRepository).saveAndFlush(reservationCaptor.capture());
        Reservation updatedReservation = reservationCaptor.getValue();
        assertEquals(StatusEnum.CONFIRMED, updatedReservation.getStatus());
        assertFalse(updatedReservation.getTables().isEmpty());
        assertFalse(tableEntity.isAvailable());
    }

    @Test
    void testRemoveAllOlderThanNow() {
        // Act
        toTest.removeAllOlderThanNow();

        // Assert
        verify(reservationRepository).deleteAllByEvent_DateLessThan(LocalDate.now());
    }


}
