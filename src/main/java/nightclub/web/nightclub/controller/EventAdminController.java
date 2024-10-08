package nightclub.web.nightclub.controller;

import jakarta.validation.Valid;
import nightclub.web.nightclub.entities.Event;
import nightclub.web.nightclub.entities.Image;
import nightclub.web.nightclub.entities.dtos.EditEventDTO;
import nightclub.web.nightclub.entities.dtos.EventDTO;
import nightclub.web.nightclub.entities.dtos.EventDetailsDTO;
import nightclub.web.nightclub.services.EventService;
import nightclub.web.nightclub.services.ImageService;
import nightclub.web.nightclub.services.SingerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Set;

@Controller
@RequestMapping("/admin/events")
public class EventAdminController {

    private final EventService eventService;
    private final SingerService singerService;

    private final ImageService imageService;

    public EventAdminController(EventService eventService, SingerService singerService, ImageService imageService) {
        this.eventService = eventService;
        this.singerService = singerService;
        this.imageService = imageService;
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
        if(!model.containsAttribute("EditEventDTO")){
            EditEventDTO event = eventService.findEventByIdToEdit(id);
            model.addAttribute("EditEventDTO", event);
        }

        return "events-edit";
    }

    @PostMapping("/edit")
    public String updateEvent(@Valid EditEventDTO editEventDTO,BindingResult bindingResult,RedirectAttributes redirectAttributes) {
            if(bindingResult.hasErrors()){
                redirectAttributes.addFlashAttribute("EditEventDTO",editEventDTO);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.EditEventDTO",bindingResult);
                return "redirect:/admin/events/edit/" + editEventDTO.getId();
            }
        eventService.save(editEventDTO);
        return "redirect:/admin/events";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteById(id);
        return "redirect:/admin/events";
    }
    @GetMapping("/add-image")
    public String showAddImageForm(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "add-image-to-event";
    }

    @PostMapping("/add-image")
    public String addImageToEvent(@RequestParam("eventId") Long eventId,
                                  @RequestParam("image") MultipartFile[] files) {
        Event event = eventService.getEventById(eventId).get();
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String imagePath = saveImageToFileSystem(file,event);
                    Image image = new Image();
                    image.setPathUrl(imagePath);
                    image.setEvent(event);
                    imageService.saveImage(image);
                }
            }
        }
        return "redirect:/admin/events/add-image?success";
    }

    private String saveImageToFileSystem(MultipartFile file,Event event) {
        String fileName = file.getOriginalFilename();
        String uploadDir = "C:\\Users\\qnida\\Desktop\\NightClub\\images\\" + event.getName()+ "-" + event.getId() +"\\";

        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }
        try {
            File uploadFile = new File(uploadDir + fileName);
            file.transferTo(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/images/" + event.getName()+ "-" + event.getId() +"/" + fileName;
    }


}
