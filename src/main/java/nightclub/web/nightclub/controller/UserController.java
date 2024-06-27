package nightclub.web.nightclub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/book")
    public String book(){
        return "booking";
    }
}
