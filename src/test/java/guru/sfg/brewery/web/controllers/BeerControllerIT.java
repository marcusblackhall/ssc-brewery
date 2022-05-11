package guru.sfg.brewery.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by jt on 6/12/20.
 */
@SpringBootTest
@Slf4j
//@WebMvcTest
public class BeerControllerIT extends BaseIT {


    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/beers/new").with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/createBeer"))
                .andExpect(model().attributeExists("beer"));
    }



    @Test
    void testGetIndexSlash() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test using In memory authentication")
    void inMemoryAuthenication() throws Exception {
        mockMvc.perform(get("/beers/new")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/createBeer"));
    }


    @Test
    @DisplayName("Test added new user scott")
    void inMemoryAuthenicationForScott() throws Exception {
        mockMvc.perform(get("/beers/new")
                        .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/createBeer"));
    }

    @Test
    void showLadapEncoder() {
        PasswordEncoder passwordEncoder = new LdapShaPasswordEncoder();
        System.out.println(passwordEncoder.encode("password"));
        assertTrue(passwordEncoder.matches("password", passwordEncoder.encode("password")));
    }

    @Test
    void showSha256Encoder() {
        PasswordEncoder passwordEncoder = new StandardPasswordEncoder();
        System.out.println(passwordEncoder.encode("marcus"));
        System.out.println(passwordEncoder.encode("marcus"));
        assertTrue(passwordEncoder.matches("password", passwordEncoder.encode("password")));
    }

    @WithMockUser("spring")
    @DisplayName("Useing mocked authenticated user")
    @Test
    void findBeers() throws Exception {
        mockMvc.perform(get("/beers/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
    }

    @WithMockUser("test")
    @DisplayName("Useing mocked authenticated user")
    @Test
    void findBeersWithOtherUser() throws Exception {
        mockMvc.perform(get("/beers/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    @DisplayName("Test using the spring config")
    void findBeersWithAuthentication() throws Exception {
        mockMvc.perform(get("/beers/find")
                        .with(httpBasic("marcus", "marcus")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    @DisplayName("Mvc Matcher on beers upc")
    void shouldAllowFindBeersUpc() throws Exception {

        mockMvc.perform(get("/api/v1/beerUpc/0083783375213"))
                .andExpect(status().isOk());
    }

}
