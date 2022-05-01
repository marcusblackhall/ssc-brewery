package guru.sfg.brewery;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
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
    void demoBcrypt15(){

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);
        System.out.println(passwordEncoder.encode("tiger"));
        System.out.println(passwordEncoder.encode("tiger"));


    }

    @Test
    void delegatePasswordEncoder(){

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
