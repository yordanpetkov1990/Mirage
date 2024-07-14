package nightclub_menu.service.impl;

import nightclub_menu.model.MenuItem;
import nightclub_menu.model.dto.AddMenuItemDTO;
import nightclub_menu.model.dto.MenuItemDTO;
import nightclub_menu.model.enums.MenuItemCategory;
import nightclub_menu.repository.MenuItemRepository;
import nightclub_menu.service.MenuService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuItemRepository;

    public MenuServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public MenuItemDTO getMenuItemById(Long id) {
       return this.menuItemRepository.findById(id).map(MenuServiceImpl::map).orElseThrow(IllegalArgumentException::new);


    }

    @Override
    public MenuItemDTO createMenuItem(AddMenuItemDTO addMenuItemDTO) {
        MenuItem menuItem = menuItemRepository.save(map(addMenuItemDTO));
        return map(menuItem);
    }

    @Override
    public List<MenuItemDTO> getAllMenuItems() {
        return this.menuItemRepository.findAll()
                .stream()
                .map(MenuServiceImpl::map)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMenuItem(Long id) {
        this.menuItemRepository.deleteById(id);
    }

    @Override
    public List<MenuItemDTO> getAllMenuItemsByCategory(String category) {
        return this.menuItemRepository.findAllByCategory(MenuItemCategory.valueOf(category.toUpperCase()))
                .stream().map(MenuServiceImpl::map)
                .collect(Collectors.toList());
    }

    private static MenuItemDTO map(MenuItem menuItem) {
        return new MenuItemDTO(menuItem.getId(),menuItem.getPrice(),menuItem.getVolume());
    }
    private static MenuItem map(AddMenuItemDTO addMenuItemDTO) {
        return new MenuItem().setCategory(addMenuItemDTO.menuItemCategory()).setPrice(addMenuItemDTO.price()).setVolume(addMenuItemDTO.volume());
    }
}
