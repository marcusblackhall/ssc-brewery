package guru.sfg.brewery;

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class BeerRestControllerIT extends BaseIT {

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
}
