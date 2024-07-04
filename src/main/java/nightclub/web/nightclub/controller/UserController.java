package nightclub.web.nightclub.controller;

import jakarta.validation.Valid;
import nightclub.web.nightclub.entities.dtos.RegistrationDTO;
import nightclub.web.nightclub.services.userService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final userService userService;

    public UserController(userService userService) {
        this.userService = userService;
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

}
