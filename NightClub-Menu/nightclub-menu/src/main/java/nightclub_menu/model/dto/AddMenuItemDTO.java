package nightclub_menu.model.dto;

import nightclub_menu.model.enums.MenuItemCategory;

import java.math.BigDecimal;

public record AddMenuItemDTO(
        BigDecimal price,
        MenuItemCategory menuItemCategory,
        String volume
) {

}
