package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.repositories.BeerInventoryRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.services.BeerService;
import guru.sfg.brewery.services.BreweryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by jt on 6/12/20.
 */
@WebMvcTest
public class BeerControllerIT extends BaseIT {




    @MockBean
    BeerRepository beerRepository;

    @MockBean
    BeerInventoryRepository beerInventoryRepository;

    @MockBean
    BreweryService breweryService;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    BeerService beerService;


    @Test
    @DisplayName("Test using In memory authentication")
    void inMemoryAuthenication() throws Exception{
        mockMvc.perform(get("/beers/new")
                        .with(httpBasic("user","password")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/createBeer"));
    }



    @WithMockUser("spring")
    @DisplayName("Useing mocked authenticated user")
    @Test
    void findBeers() throws Exception{
        mockMvc.perform(get("/beers/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
    }

    @WithMockUser("test")
    @DisplayName("Useing mocked authenticated user")
    @Test
    void findBeersWithOtherUser() throws Exception{
        mockMvc.perform(get("/beers/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
    }
    @Test
    @DisplayName("Test using the spring config")
    void findBeersWithAuthentication() throws Exception{
        mockMvc.perform(get("/beers/find")
                        .with(httpBasic("marcus","marcus")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    @DisplayName("Mvc Matcher on beers upc")
    void shouldAllowFindBeersUpc() throws Exception {

        mockMvc.perform(get("/api/v1/beerUpc/111122"))
                .andExpect(status().isOk());
    }

}
