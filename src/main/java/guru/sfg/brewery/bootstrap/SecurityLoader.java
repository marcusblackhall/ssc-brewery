package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Slf4j
@Component

public class SecurityLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        addTestData();
        listAllUsers();

    }

    private void listAllUsers() {

        List<User> all = userRepository.findAll();
        all.forEach(user -> log.info("user {}", user.getUsername()));
    }

    private void addTestData() {

        if (authorityRepository.count() != 0) {
            return;
        }
        Authority adminAuthority = Authority.builder().role("ROLE_ADMIN").build();
        authorityRepository.save(adminAuthority);

        Authority customerAuthority = Authority.builder().role("ROLE_CUSTOMER").build();
        authorityRepository.save(customerAuthority);

        Authority userAuthority = Authority.builder().role("ROLE_USER").build();
        authorityRepository.save(userAuthority);


        User adminUser = new User();
        adminUser.setUsername("marcus");
        adminUser.setPassword(passwordEncoder.encode("marcus"));
        adminUser.setAuthorities(Set.of((adminAuthority)));

        User customerUser
                = User.builder().username("scott").password(passwordEncoder.encode("tiger")).authority(customerAuthority).build();


        User userUser = new User();
        userUser.setUsername("user");
        userUser.setPassword(passwordEncoder.encode("password"));
        userUser.setAuthorities(Set.of((userAuthority)));

        userRepository.saveAll(List.of(adminUser, customerUser, userUser));

        log.info("No. users created {}",userRepository.count());
    }


}
