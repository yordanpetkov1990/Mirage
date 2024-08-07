package nightclub.web.nightclub.controller;

import nightclub.web.nightclub.entities.*;
import nightclub.web.nightclub.entities.dtos.EditReservationDTO;
import nightclub.web.nightclub.services.ReservationService;
import nightclub.web.nightclub.services.TableService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationAdminControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private TableService tableService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowReservationsWithPendingReservation() throws Exception {
        // Prepare test data
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setStatus(StatusEnum.PENDING);
        reservation.setOwner(new User().setUsername("penyo"));
        Event event = new Event();
        event.setName("cisco");
        reservation.setEvent(event);

        TableEntity table1 = new TableEntity();
        table1.setId(1L);
        TableEntity table2 = new TableEntity();
        table2.setId(2L);
        List<TableEntity> freeTables = Arrays.asList(table1, table2);
        Pageable pageable = PageRequest.of(1, 1, Sort.by(Sort.Direction.ASC, "createdAt"));

        Mockito.when(reservationService.findReservationsByEventAndStatus(1L, pageable)).thenReturn(new PageImpl<>(Collections.singletonList(reservation), pageable, 1));
        Mockito.when(tableService.findAllFreeTables(reservation)).thenReturn(freeTables);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/reservations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("manage-reservations"));
    }

        @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowReservationsNoNewReservations() throws Exception {
            Pageable pageable = PageRequest.of(1, 1, Sort.by(Sort.Direction.ASC, "createdAt"));
        Mockito.when(reservationService.findReservationsByEventAndStatus(1L, pageable)).thenReturn(Page.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/reservations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("manage-reservations"));
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowReservationsNoAvailableTables() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setStatus(StatusEnum.PENDING);

        Mockito.when(reservationService.findFirstCreatedPendingReservation()).thenReturn(Optional.of(reservation));
        Mockito.when(tableService.findAllFreeTables(reservation)).thenReturn(Arrays.asList());

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/reservations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("manage-reservations"));
    }



}
