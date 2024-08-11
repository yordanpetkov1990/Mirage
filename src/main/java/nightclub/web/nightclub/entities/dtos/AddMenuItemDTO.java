package nightclub.web.nightclub.entities.dtos;


import nightclub.web.nightclub.entities.MenuItemCategory;

import java.math.BigDecimal;

public class AddMenuItemDTO {
    private String name;
    private BigDecimal price;
    private MenuItemCategory menuItemCategory;
    private String volume;

    public static AddMenuItemDTO empty() {
        return new AddMenuItemDTO();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AddMenuItemDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public MenuItemCategory getMenuItemCategory() {
        return menuItemCategory;
    }

    public AddMenuItemDTO setMenuItemCategory(MenuItemCategory menuItemCategory) {
        this.menuItemCategory = menuItemCategory;
        return this;
    }

    public String getVolume() {
        return volume;
    }

    public AddMenuItemDTO setVolume(String volume) {
        this.volume = volume;
        return this;
    }

    public String getName() {
        return name;
    }

    public AddMenuItemDTO setName(String name) {
        this.name = name;
        return this;
    }
}
