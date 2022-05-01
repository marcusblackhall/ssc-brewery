package guru.sfg.brewery;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

class PasswordEncoderTest {

    @Test
    void demoBcrypt(){

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("password"));
        System.out.println(passwordEncoder.encode("password"));
        System.out.println(passwordEncoder.encode("marcus"));
        System.out.println(passwordEncoder.encode("marcus"));


    }

     @Test
     void demoShaPasswordEncoder(){
         PasswordEncoder passwordEncoder = new StandardPasswordEncoder();
         System.out.println(passwordEncoder.encode("password"));
         System.out.println(passwordEncoder.encode("password"));

     }

}
