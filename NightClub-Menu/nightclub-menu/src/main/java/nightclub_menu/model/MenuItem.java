package nightclub_menu.model;


import jakarta.persistence.*;
import nightclub_menu.model.enums.MenuItemCategory;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuItemCategory category;

    @Column(nullable = false)
    private String volume;

    public MenuItem() {
    }

    public Long getId() {
        return id;
    }

    public MenuItem setId(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MenuItem setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public MenuItemCategory getCategory() {
        return category;
    }

    public MenuItem setCategory(MenuItemCategory category) {
        this.category = category;
        return this;
    }

    public String getVolume() {
        return volume;
    }

    public MenuItem setVolume(String volume) {
        this.volume = volume;
        return this;
    }
}
