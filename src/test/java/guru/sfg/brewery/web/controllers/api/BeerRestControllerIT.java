package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.controllers.BaseIT;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@WebMvcTest <== only web contect
public class BeerRestControllerIT extends BaseIT {

    @Autowired
    BeerRepository beerRepository;

    Beer createBeer(){
        Random rand = new Random();
        return beerRepository.saveAndFlush(Beer.builder()
                        .beerName("mybeer")
                        .beerStyle(BeerStyleEnum.IPA)
                        .upc(String.valueOf(rand.nextInt(99999999)))
                .build());

    }

    @Test
    void deleteBeerHttpBasicForAdminUser() throws Exception{
        mockMvc.perform(delete("/api/v1/beer/" + createBeer().getId())
                .with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    void deleteBeerHttpBasicUserRole() throws Exception{

        mockMvc.perform(delete("/api/v1/beer/" + createBeer().getId())
                .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteBeerHttpasicCustomerRole() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/" + createBeer().getId())
                        .with(httpBasic("scott","tiger")))
                .andExpect(status().isForbidden());

    }


    @Test
    void deleteBeersBadCredentials() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/" + createBeer().getId()))
                .andExpect(status().isUnauthorized());
    }



    @Test
    void deleteBeer() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/" + createBeer().getId())
                .with(httpBasic("spring","guru"))
        ).andExpect(status().is2xxSuccessful());

    }

//    @Test
//    void deleteBeerUsingUrlParams() throws Exception {
//
//        mockMvc.perform(delete("/api/v1/beer/" + createBeer().getId())
//        ).andExpect(status().isOk());
//
//    }

//    @Test
//    void deleteBeerUsingUrlParamsAsUnauthorised() throws Exception {
//
//        mockMvc.perform(delete("/api/v1/beer/" + createBeer().getId())
//        ).andExpect(status().isUnauthorized());
//
//    }
}
