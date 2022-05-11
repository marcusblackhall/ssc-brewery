package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jt on 6/13/20.
 */
@SpringBootTest
//@WebMvcTest
public class IndexControllerIT extends BaseIT {


    @Test
    void testGetIndexSlash() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindBeersIsAvailable() throws Exception {
        mockMvc.perform(get("/beers/find").with(httpBasic("marcus","marcus")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAllowFindBeersWithApi() throws Exception {

        mockMvc.perform(get("/api/v1/beer").with(httpBasic("marcus","marcus")))
                .andExpect(status().isOk());
    }
}
