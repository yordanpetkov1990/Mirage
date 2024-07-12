package nightclub_menu.model.dto;

import java.math.BigDecimal;

public record MenuItemDTO(
        Long id,
        BigDecimal price,
        String volume
) {
}
