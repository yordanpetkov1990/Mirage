package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.dtos.AddMenuItemDTO;
import nightclub.web.nightclub.services.MenuItemService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final RestClient restClient;

    public MenuItemServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void createMenuItem(AddMenuItemDTO addMenuItemDTO) {
        restClient
                .post()
                .uri("http://localhost:8081/menu")
                .body(addMenuItemDTO)
                .retrieve();
    }
}
