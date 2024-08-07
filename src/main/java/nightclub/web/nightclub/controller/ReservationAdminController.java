package nightclub.web.nightclub.controller;

import nightclub.web.nightclub.entities.Reservation;
import nightclub.web.nightclub.entities.Singer;
import nightclub.web.nightclub.entities.StatusEnum;
import nightclub.web.nightclub.entities.TableEntity;
import nightclub.web.nightclub.entities.dtos.EditReservationDTO;
import nightclub.web.nightclub.entities.dtos.ReservationDTO;
import nightclub.web.nightclub.services.EventService;
import nightclub.web.nightclub.services.ReservationService;
import nightclub.web.nightclub.services.SingerService;
import nightclub.web.nightclub.services.TableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin/reservations")
public class ReservationAdminController {

    private final ReservationService reservationService;

    private final TableService tableService;

    private final EventService eventService;

    public ReservationAdminController(ReservationService reservationService, TableService tableService, EventService eventService) {
        this.reservationService = reservationService;
        this.tableService = tableService;
        this.eventService = eventService;
    }



    @GetMapping
    public String showReservationsForEvent(
            @RequestParam(value = "eventId",required = false) Long eventId ,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model
    ) {
        model.addAttribute("events", eventService.getAllFutureEvents());
        if(eventId != null){
            Pageable pageable = PageRequest.of(page, 1, Sort.by(Sort.Direction.ASC, "createdAt"));
            Page<Reservation> reservationPage = reservationService.findReservationsByEventAndStatus(eventId, pageable);

            if (reservationPage.hasContent()) {
                List<TableEntity> tables = tableService.findAllFreeTables(reservationPage.getContent().get(0));
                if(!tables.isEmpty()){
                    model.addAttribute("reservation", reservationPage.getContent().get(0));
                    model.addAttribute("eventName", reservationPage.getContent().get(0).getEvent().getName());
                    model.addAttribute("eventDate", reservationPage.getContent().get(0).getEvent().getDate());
                    model.addAttribute("eventId", eventId);
                    model.addAttribute("tables", tables);
                    model.addAttribute("statuses", StatusEnum.values());
                    model.addAttribute("currentPage", page);
                    model.addAttribute("totalPages", reservationPage.getTotalPages());
                }else{
                    model.addAttribute("noAvailableTables",true);
                }

            } else {
                model.addAttribute("noReservations", true);
            }
        }


        return "manage-reservations";
    }

    @PostMapping("/update")
    public String updateReservation(EditReservationDTO reservationDTO) {
        reservationService.updateReservation(reservationDTO);
        return "redirect:/admin/reservations";
    }
}
