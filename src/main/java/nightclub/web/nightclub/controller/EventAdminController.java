package nightclub.web.nightclub.controller;

import nightclub.web.nightclub.entities.Event;
import nightclub.web.nightclub.entities.dtos.EventDTO;
import nightclub.web.nightclub.entities.dtos.EventDetailsDTO;
import nightclub.web.nightclub.services.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/events")
public class EventAdminController {

    private final EventService eventService;

    public EventAdminController(EventService eventService) {
        this.eventService = eventService;
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
//
//    @PostMapping("/edit/{id}")
//    public String updateEvent(@PathVariable Long id, @ModelAttribute EventDTO eventDTO) {
//        eventDTO.setId(id);
//        eventService.save(eventDTO);
//        return "redirect:/admin/events";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteEvent(@PathVariable Long id) {
//        eventService.deleteById(id);
//        return "redirect:/admin/events";
//    }


}
