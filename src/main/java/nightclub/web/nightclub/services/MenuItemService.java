package nightclub.web.nightclub.services;

import nightclub.web.nightclub.entities.MenuItem;
import nightclub.web.nightclub.entities.dtos.AddMenuItemDTO;

import java.util.List;

public interface MenuItemService {
    void createMenuItem(AddMenuItemDTO addMenuItemDTO);

    List<MenuItem> getAllMenuItemsByCategory(String category);
}
