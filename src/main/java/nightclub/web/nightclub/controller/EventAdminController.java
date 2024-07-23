package nightclub.web.nightclub.controller;

import nightclub.web.nightclub.entities.Event;
import nightclub.web.nightclub.entities.dtos.EventDTO;
import nightclub.web.nightclub.entities.dtos.EventDetailsDTO;
import nightclub.web.nightclub.services.EventService;
import nightclub.web.nightclub.services.SingerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/admin/events")
public class EventAdminController {

    private final EventService eventService;
    private final SingerService singerService;

    public EventAdminController(EventService eventService, SingerService singerService) {
        this.eventService = eventService;
        this.singerService = singerService;
    }

    @ModelAttribute("allSingers")
    public Set<String> getAllSingers(){
        return this.singerService.getAvailableSingers();
    }
    @GetMapping
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.getAllDetailedEvents());
        return "list-events";
    }

    @GetMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id, Model model) {
        EventDetailsDTO event = eventService.findEventById(id).get();
        model.addAttribute("event", event);
        return "events-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateEvent(@PathVariable Long id, @ModelAttribute EventDetailsDTO eventDTO) {
        eventDTO.setId(id);
        eventService.save(eventDTO);
        return "redirect:/admin/events";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteById(id);
        return "redirect:/admin/events";
    }


}
