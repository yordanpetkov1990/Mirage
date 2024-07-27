package nightclub.web.nightclub.controller;

import nightclub.web.nightclub.services.FAQService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final FAQService faqService;

    public HomeController(FAQService faqService) {
        this.faqService = faqService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }
    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/faq")
    public String faq(Model model){
        model.addAttribute("faqs", faqService.getAllFAQs());
        return "faq";
    }
}
