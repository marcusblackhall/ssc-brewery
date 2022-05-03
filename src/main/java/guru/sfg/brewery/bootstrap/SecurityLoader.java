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

        deleteAll();
        addTestData();
        listAllUsers();

    }

    private void listAllUsers() {

        List<User> all = userRepository.findAll();
        all.forEach(user -> log.info("user {}", user.getUsername()));
    }

    private void addTestData() {
        Authority authority1 = new Authority();
        authority1.setRole("ADMIN");
        authorityRepository.save(authority1);

        Authority authority2 = new Authority();
        authority2.setRole("CUSTOMER");
        authorityRepository.save(authority2);

        User user1 = new User();
        user1.setUsername("marcus");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user1.setPassword(bCryptPasswordEncoder.encode("marcus"));
        user1.setAuthorities(Set.of((authority1)));

        User user2 = new User();
        user2.setUsername("scott");
        BCryptPasswordEncoder bCryptPasswordEncoder15 = new BCryptPasswordEncoder(15);
        user2.setPassword(bCryptPasswordEncoder15.encode("tiger"));
        user2.setAuthorities(Set.of((authority1)));

        User user3 = new User();
        user3.setUsername("user");
        user3.setPassword(bCryptPasswordEncoder.encode("password"));
        user3.setAuthorities(Set.of((authority2)));

        userRepository.saveAll(List.of(user1, user2, user3));

        log.info("created user marcus");
    }

    private void deleteAll() {
        authorityRepository.deleteAll();
        userRepository.deleteAll();
        log.info("Deleted all users and authorities");
    }


}
