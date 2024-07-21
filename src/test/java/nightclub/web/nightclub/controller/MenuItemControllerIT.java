package nightclub.web.nightclub.controller;

import jakarta.validation.Valid;
import nightclub.web.nightclub.entities.MenuItem;
import nightclub.web.nightclub.entities.MenuItemCategory;
import nightclub.web.nightclub.entities.UserRoleEnum;
import nightclub.web.nightclub.entities.dtos.AddMenuItemDTO;
import nightclub.web.nightclub.services.MenuItemService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Collections;
import java.util.List;

import static nightclub.web.nightclub.entities.UserRoleEnum.ADMIN;
import static org.mockito.Mockito.when;
@SpringBootTest
@AutoConfigureMockMvc
public class MenuItemControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MenuItemService menuItemService;

    @InjectMocks
    private MenuItemController menuItemController;

    @Autowired
    private InternalResourceViewResolver viewResolver;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testNewMenuItem() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/menu-item/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("menu-item-add"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categoryList"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("addMenuItemDTO"));
    }





    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testShowMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/menu"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("menu-categories"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testShowMenuItems() throws Exception {
        String category = "DRINK";
        List<MenuItem> items = Collections.singletonList(new MenuItem());
        when(menuItemService.getAllMenuItemsByCategory(category)).thenReturn(items);

        mockMvc.perform(MockMvcRequestBuilders.get("/menu-items")
                        .param("category", category))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("menu-items"))
                .andExpect(MockMvcResultMatchers.model().attribute("category", category))
                .andExpect(MockMvcResultMatchers.model().attributeExists("menuItems"));
    }
}
