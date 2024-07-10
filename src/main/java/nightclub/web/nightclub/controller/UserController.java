package nightclub.web.nightclub.controller;

import jakarta.validation.Valid;
import nightclub.web.nightclub.entities.Reservation;
import nightclub.web.nightclub.entities.UserDetails.UserDetailsEntity;
import nightclub.web.nightclub.entities.dtos.RegistrationDTO;
import nightclub.web.nightclub.entities.dtos.ShowReservationDTO;
import nightclub.web.nightclub.services.ReservationService;
import nightclub.web.nightclub.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final ReservationService reservationService;

    public UserController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }


    @ModelAttribute("registrationDTO")
    public RegistrationDTO registrationDTO(){
        return new RegistrationDTO();
    }

    @GetMapping("/register")
    public String register(){
        return "registration";
    }

    @PostMapping("/register")
    public String doRegister(@Valid RegistrationDTO registrationDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("registrationDTO",registrationDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registrationDTO",bindingResult);
            return "redirect:/register";
        }

        userService.registerUser(registrationDTO);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/reservations")
    public String showUserReservations(
            @RequestParam(value = "status", required = false) String status,
            Model model,
            @AuthenticationPrincipal UserDetailsEntity userDetailsEntity)
    {

        List<ShowReservationDTO> reservationsByUserAndStatus = reservationService.getReservationsByUserAndStatus(userDetailsEntity.getId(), status);

        model.addAttribute("reservations", reservationsByUserAndStatus);
        model.addAttribute("status", status);
        return "my-reservations";
    }

}
