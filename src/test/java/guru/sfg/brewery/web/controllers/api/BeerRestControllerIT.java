package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@WebMvcTest <== only web contect
public class BeerRestControllerIT extends BaseIT {

    @Test
    void deleteBeerHttpBasicForAdminUser() throws Exception{
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                .with(httpBasic("marcus", "marcus")))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    void deleteBeerHttpBasicUserRole() throws Exception{

        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteBeerHttpasicCustomerRole() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/555")
                        .with(httpBasic("scott","tiger")))
                .andExpect(status().isForbidden());

    }


    @Test
    void deleteBeersBadCredentials() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/555")
                        .header("Api-key", "guruxxx")
                        .header("Api-secret", "whatever"))
                .andExpect(status().isUnauthorized());
    }



    @Test
    void deleteBeer() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/5555")
                .header("Api-Key", "scott")
                .header("Api-Secret", "tiger")
        ).andExpect(status().isOk());

    }

    @Test
    void deleteBeerUsingUrlParams() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/5555")
                .param("Api-Key", "scott")
                .param("Api-Secret", "tiger")
        ).andExpect(status().isOk());

    }

    @Test
    void deleteBeerUsingUrlParamsAsUnauthorised() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/5555")
                .param("Api-Key", "scotty")
                .param("Api-Secret", "tiger")
        ).andExpect(status().isUnauthorized());

    }
}
