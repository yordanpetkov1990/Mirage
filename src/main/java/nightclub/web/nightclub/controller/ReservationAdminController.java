package nightclub.web.nightclub.controller;

import nightclub.web.nightclub.entities.Reservation;
import nightclub.web.nightclub.entities.Singer;
import nightclub.web.nightclub.entities.StatusEnum;
import nightclub.web.nightclub.entities.TableEntity;
import nightclub.web.nightclub.entities.dtos.EditReservationDTO;
import nightclub.web.nightclub.entities.dtos.ReservationDTO;
import nightclub.web.nightclub.services.ReservationService;
import nightclub.web.nightclub.services.SingerService;
import nightclub.web.nightclub.services.TableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin/reservations")
public class ReservationAdminController {

    private final ReservationService reservationService;

    private final TableService tableService;

    public ReservationAdminController(ReservationService reservationService, TableService tableService) {
        this.reservationService = reservationService;
        this.tableService = tableService;
    }

    @GetMapping
    public String showReservations(Model model) {
        Optional<Reservation> firstCreatedPendingReservation = reservationService.findFirstCreatedPendingReservation();

        if(firstCreatedPendingReservation.isPresent()){
            List<TableEntity> tables = tableService.findAllFreeTables(firstCreatedPendingReservation.get());
            if(!tables.isEmpty()){
                model.addAttribute("reservation", firstCreatedPendingReservation.get());
                model.addAttribute("tables", tables);
                model.addAttribute("statuses", StatusEnum.values());
            }else{
                model.addAttribute("noAvailableTables", true);
            }
        }else{
            model.addAttribute("noNewReservations", true);
        }

        return "manage-reservations";
    }
    @PostMapping("/update")
    public String updateReservation(EditReservationDTO reservationDTO) {
        reservationService.updateReservation(reservationDTO);
        return "redirect:/admin/reservations";
    }
}
