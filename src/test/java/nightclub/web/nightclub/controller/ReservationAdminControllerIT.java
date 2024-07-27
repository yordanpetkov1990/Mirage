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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
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
        reservation.setEvent(new Event().setName("cisco"));

        TableEntity table1 = new TableEntity();
        table1.setId(1L);
        TableEntity table2 = new TableEntity();
        table2.setId(2L);
        List<TableEntity> freeTables = Arrays.asList(table1, table2);

        Mockito.when(reservationService.findFirstCreatedPendingReservation()).thenReturn(Optional.of(reservation));
        Mockito.when(tableService.findAllFreeTables(reservation)).thenReturn(freeTables);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/reservations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("manage-reservations"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("reservation"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("tables"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("statuses"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowReservationsNoNewReservations() throws Exception {
        Mockito.when(reservationService.findFirstCreatedPendingReservation()).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/reservations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("manage-reservations"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("noNewReservations"));
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
                .andExpect(MockMvcResultMatchers.view().name("manage-reservations"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("noAvailableTables"));
    }



}
