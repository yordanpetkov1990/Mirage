package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.MenuItem;
import nightclub.web.nightclub.entities.MenuItemCategory;
import nightclub.web.nightclub.entities.dtos.AddMenuItemDTO;
import nightclub.web.nightclub.services.MenuItemService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

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

    @Override
    public List<MenuItem> getAllMenuItemsByCategory(String category) {
        return restClient
                .get()
                .uri("http://localhost:8081/menu/by-category/{category}",category)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    @Override
    public MenuItemCategory[] getAllCategories() {
        return restClient
                .get()
                .uri("http://localhost:8081/menu/categories")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    @Override
    public void deleteMenuItemById(String id) {
        restClient
                .delete()
                .uri("http://localhost:8081/menu/{id}", id)
                .retrieve();
    }

    @Override
    public MenuItem getMenuItemById(String id) {
        return restClient
                .get()
                .uri("http://localhost:8081/menu/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(MenuItem.class);
    }
}
