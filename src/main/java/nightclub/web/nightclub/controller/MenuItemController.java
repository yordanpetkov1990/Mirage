package nightclub.web.nightclub.controller;

import jakarta.validation.Valid;
import nightclub.web.nightclub.entities.MenuItemCategory;
import nightclub.web.nightclub.entities.dtos.AddMenuItemDTO;
import nightclub.web.nightclub.services.MenuItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MenuItemController {

    public final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }
    @ModelAttribute("categoryList")
    public MenuItemCategory[] menuItemCategories(){
        return MenuItemCategory.values();
    }

    @GetMapping("/admin/menu-item/add")
    public String newMenuItem(Model model) {

        if (!model.containsAttribute("addMenuItemDTO")) {
            model.addAttribute("addMenuItemDTO", AddMenuItemDTO.empty());
        }

        return "menu-item-add";
    }

    @PostMapping("/admin/menu-item/add")
    public String createOffer(
            @Valid AddMenuItemDTO addMenuItemDTO,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if(bindingResult.hasErrors()){
            rAtt.addFlashAttribute("addMenuItemDTO", addMenuItemDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addMenuItemDTO", bindingResult);
            return "redirect:/admin/menu-item/add";
        }


        menuItemService.createMenuItem(addMenuItemDTO);

        return "redirect:/";
    }
}
