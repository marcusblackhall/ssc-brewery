package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BreweriesControllerIT extends BaseIT {

    @Test
    @DisplayName("Admin can view breweries")
    void onlyCustomersCanListBreweries() throws Exception {

        mockMvc.perform(get("/brewery/breweries").with(httpBasic("marcus","marcus")))
                .andExpect(status().isForbidden());

    }

    @Test
    @DisplayName("Customer can list breweries")
    void aCustomerCanListBreweries() throws Exception {

        mockMvc.perform(get("/brewery/breweries").with(httpBasic("scott","tiger")))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @DisplayName("Rest Customer can list breweries")
    void restCustomerCanListBreweries() throws Exception {

        mockMvc.perform(get("/brewery/api/v1/breweries").with(httpBasic("scott","tiger")))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @DisplayName("Rest User cannot breweries")
    void restUserCannotListBreweries() throws Exception {

        mockMvc.perform(get("/brewery/api/v1/breweries").with(httpBasic("user","password")))
                .andExpect(status().isForbidden());

    }

    @Test
    @DisplayName("Unauthenticated Usr cannot list breweries")
    void unauhtenticatedUserCannotListBreweries() throws Exception {

        mockMvc.perform(get("/brewery/api/v1/breweries").with(httpBasic("nobody","password")))
                .andExpect(status().isUnauthorized());

    }
}
