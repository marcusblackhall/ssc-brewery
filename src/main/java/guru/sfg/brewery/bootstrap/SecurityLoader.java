package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Slf4j
@Component

public class SecurityLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

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
        Authority authority1 = new Authority();
        authority1.setRole("ADMIN");
        authorityRepository.save(authority1);

        Authority authority2 = Authority.builder().role("CUSTOMER").build();
        authorityRepository.save(authority2);


        User user1 = new User();
        user1.setUsername("marcus");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user1.setPassword(bCryptPasswordEncoder.encode("marcus"));
        user1.setAuthorities(Set.of((authority1)));

        BCryptPasswordEncoder bCryptPasswordEncoder15 = new BCryptPasswordEncoder(15);
        User user2 = User.builder().username("scott").password(bCryptPasswordEncoder15.encode("tiger")).authority(authority1).build();


        User user3 = new User();
        user3.setUsername("user");
        user3.setPassword(bCryptPasswordEncoder.encode("password"));
        user3.setAuthorities(Set.of((authority2)));

        userRepository.saveAll(List.of(user1, user2, user3));

        log.info("No. users created {}",userRepository.count());
    }


}
