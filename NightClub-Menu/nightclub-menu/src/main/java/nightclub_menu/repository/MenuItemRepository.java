package nightclub_menu.repository;

import nightclub_menu.model.MenuItem;
import nightclub_menu.model.dto.MenuItemDTO;
import nightclub_menu.model.enums.MenuItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
    List<MenuItem> findAllByCategory(MenuItemCategory category);
}
