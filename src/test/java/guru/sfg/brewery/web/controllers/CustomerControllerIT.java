package guru.sfg.brewery.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
public class CustomerControllerIT extends BaseIT {

    @ParameterizedTest(name = "#{index} with [{arguments}]")
    @MethodSource("guru.sfg.brewery.web.controllers.BaseIT#getStreamOfAdminCustomers")
    void shouldAllowCustomerAndAdmin(String user,String password) throws Exception {
        log.info("{} {}",user,password);
        mockMvc.perform(get("/customers")
                        .with(httpBasic(user,password)))
                .andExpect(status().isOk());
    }
    @Test
    void notLoggedInCannotListCustomers() throws Exception {
        mockMvc.perform(get("/customers")).andExpect(status().isUnauthorized());
    }

    @ParameterizedTest(name = "#{index} with [{arguments}]")
    @MethodSource("guru.sfg.brewery.web.controllers.BaseIT#getStreamOfUsers")
    void shouldForbidUserToListCustomers(String user,String password) throws Exception {
        log.info("{} {}",user,password);
        mockMvc.perform(get("/customers")
                        .with(httpBasic(user,password)))
                .andExpect(status().isForbidden());
    }

}
