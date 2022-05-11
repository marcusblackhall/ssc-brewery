package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

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
        Authority adminAuthority = Authority.builder().permission("ROLE_ADMIN").build();
        authorityRepository.save(adminAuthority);

        Authority customerAuthority = Authority.builder().permission("ROLE_CUSTOMER").build();
        authorityRepository.save(customerAuthority);

        Authority userAuthority = Authority.builder().permission("ROLE_USER").build();
        authorityRepository.save(userAuthority);

        User adminUser = User.builder().username("marcus").password(passwordEncoder.encode("marcus"))
                .authority(adminAuthority).build();

        User customerUser = User.builder().username("scott").password(passwordEncoder.encode("tiger"))
                .authority(customerAuthority)
                .build();

        User userUser = User.builder().username("user").password(passwordEncoder.encode("password"))
                .authority((userAuthority))
                .build();

        userRepository.saveAll(List.of(adminUser, customerUser, userUser));

        log.info("No. users created {}", userRepository.count());
        assert userRepository.count() == 3;
    }


}
