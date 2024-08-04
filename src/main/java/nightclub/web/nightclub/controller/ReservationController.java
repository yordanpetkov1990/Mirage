package nightclub.web.nightclub.controller;

import jakarta.validation.Valid;
import nightclub.web.nightclub.entities.Event;
import nightclub.web.nightclub.entities.UserDetails.UserDetailsEntity;
import nightclub.web.nightclub.entities.dtos.EventDTO;
import nightclub.web.nightclub.entities.dtos.EventDetailsDTO;
import nightclub.web.nightclub.entities.dtos.ReservationDTO;
import nightclub.web.nightclub.services.EventService;
import nightclub.web.nightclub.services.ReservationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.Set;

@Controller
public class ReservationController {

    private final EventService eventService;
    private final ReservationService reservationService;

    public ReservationController(EventService eventService, ReservationService reservationService) {
        this.eventService = eventService;
        this.reservationService = reservationService;
    }


    @GetMapping("/booking")
    public String makeReservation(
            Model model,
            @AuthenticationPrincipal UserDetailsEntity userDetailsEntity,
            @RequestParam(value = "event_id") Long eventId
    ) {

        if (!model.containsAttribute("ReservationDTO")) {
            EventDetailsDTO eventDetailsDTO = this.eventService.findEventById(eventId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid event ID"));

            ReservationDTO reservationDTO = new ReservationDTO()
                    .setPhoneNumber(userDetailsEntity.getPhoneNumber())
                    .setEventName(eventDetailsDTO.getName() + " - " +eventDetailsDTO.getDate())
                    .setEventId(eventId);

            model.addAttribute("ReservationDTO", reservationDTO);
        }
        return "booking";
    }


    @PostMapping("/booking")
    public String doMakeReservation(
            @Valid ReservationDTO reservationDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserDetailsEntity userDetailsEntity
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("ReservationDTO", reservationDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ReservationDTO", bindingResult);
            return "redirect:/booking?event_id=" + reservationDTO.getEventId();
        }


        reservationService.makeReservation(reservationDTO, userDetailsEntity);
        return "redirect:/";
    }


}
