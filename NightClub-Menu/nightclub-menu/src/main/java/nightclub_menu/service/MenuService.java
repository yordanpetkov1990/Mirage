package nightclub_menu.service;

import nightclub_menu.model.dto.AddMenuItemDTO;
import nightclub_menu.model.dto.MenuItemDTO;

import java.util.List;
import java.util.Optional;

public interface MenuService {
    MenuItemDTO getMenuItemById(Long id);

    MenuItemDTO createMenuItem(AddMenuItemDTO addMenuItemDTO);

    List<MenuItemDTO> getAllMenuItems();

    void deleteMenuItem(Long id);

    List<MenuItemDTO> getAllMenuItemsByCategory(String category);
}
