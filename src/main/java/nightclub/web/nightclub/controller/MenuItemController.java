package nightclub.web.nightclub.controller;

import jakarta.validation.Valid;
import nightclub.web.nightclub.entities.MenuItem;
import nightclub.web.nightclub.entities.MenuItemCategory;
import nightclub.web.nightclub.entities.dtos.AddMenuItemDTO;
import nightclub.web.nightclub.services.MenuItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MenuItemController {

    public final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }
    @ModelAttribute("categoryList")
    public MenuItemCategory[] menuItemCategories(){
        return this.menuItemService.getAllCategories();
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



    @GetMapping("menu")
    public String showMenu(){

        return "menu-categories";
    }

    @GetMapping("menu-items")
    public String showMenuItems(@RequestParam(value = "category") String category,Model model){
        List<MenuItem> allMenuItemsByCategory = menuItemService.getAllMenuItemsByCategory(category);
        model.addAttribute("category",category);
        model.addAttribute("menuItems",allMenuItemsByCategory);
        return "menu-items";
    }

    @DeleteMapping("admin/menu/delete/{id}")
    public String deleteMenuItem(@PathVariable String id,RedirectAttributes redirectAttributes){
        try {
            menuItemService.deleteMenuItemById(id);
            redirectAttributes.addFlashAttribute("message", "Item deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred while deleting the item.");
        }
        return "redirect:/menu";
    }
}
