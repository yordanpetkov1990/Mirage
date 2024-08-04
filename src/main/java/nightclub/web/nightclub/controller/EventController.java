package nightclub.web.nightclub.controller;

import jakarta.validation.Valid;
import nightclub.web.nightclub.entities.Event;
import nightclub.web.nightclub.entities.dtos.AddEventDTO;
import nightclub.web.nightclub.entities.dtos.EventDTO;
import nightclub.web.nightclub.entities.dtos.EventDetailsDTO;
import nightclub.web.nightclub.services.EventService;
import nightclub.web.nightclub.services.SingerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Controller
public class EventController {

    private final SingerService singerService;
    private final EventService eventService;

    public EventController(SingerService singerService, EventService eventService) {
        this.singerService = singerService;
        this.eventService = eventService;
    }

    @ModelAttribute("addEventDTO")
    public AddEventDTO eventDTO(){
        return new AddEventDTO();
    }

    @ModelAttribute("singers")
    public Set<String> singerDTOS(){
        return singerService.getAvailableSingers();
    }

    @GetMapping("/admin/add-event")
    public String addEvent(){
        return "add-event";
    }

    @PostMapping("/admin/add-event")
    public String doAddEvent(@Valid @ModelAttribute("addEventDTO") AddEventDTO addEventDTO,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addEventDTO", addEventDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addEventDTO", bindingResult);
            return "redirect:/admin/add-event";
        }

        eventService.registerEvent(addEventDTO);
        return "redirect:/";
    }

    @GetMapping("/events")
    public String showEvents(Model model){
        Set<EventDTO> allEvents = this.eventService.getAllEvents();
        LocalDate today = LocalDate.now();

        List<EventDTO> upcomingEvents = allEvents.stream()
                .filter(event -> LocalDate.parse(event.getEventDate()).isAfter(today) || LocalDate.parse(event.getEventDate()).isEqual(today))
                .toList();
        List<EventDTO> pastEvents = allEvents.stream()
                .filter(event -> LocalDate.parse(event.getEventDate()).isBefore(today))
                .toList();

        model.addAttribute("upcomingEvents", upcomingEvents);
        model.addAttribute("pastEvents", pastEvents);



        return "events";
    }

    @GetMapping("/event/{id}")
    public String getEventDetails(@PathVariable Long id, Model model) {
        EventDetailsDTO event = eventService.findEventById(id).get();
        model.addAttribute("event", event);
        return "event-details";
    }

}
