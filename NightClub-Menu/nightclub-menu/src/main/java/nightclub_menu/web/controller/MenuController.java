package nightclub_menu.web.controller;

import nightclub_menu.model.dto.AddMenuItemDTO;
import nightclub_menu.model.dto.MenuItemDTO;
import nightclub_menu.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(menuService.getMenuItemById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MenuItemDTO> deleteById(@PathVariable("id") Long id) {
        menuService.deleteMenuItem(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItems() {
        return ResponseEntity.ok(
                menuService.getAllMenuItems()
        );
    }
    @GetMapping("/by-category/{category}")
    public ResponseEntity<List<MenuItemDTO>> getAllByCategory(@PathVariable String category) {
        return ResponseEntity.ok(
                menuService.getAllMenuItemsByCategory(category)
        );
    }

    @PostMapping
    public ResponseEntity<MenuItemDTO> createMenuItem(
            @RequestBody AddMenuItemDTO addMenuItemDTO
    ) {

        MenuItemDTO menuItemDTO = menuService.createMenuItem(addMenuItemDTO);
        return ResponseEntity.
                created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(menuItemDTO.id())
                                .toUri()
                ).body(menuItemDTO);
    }
}
