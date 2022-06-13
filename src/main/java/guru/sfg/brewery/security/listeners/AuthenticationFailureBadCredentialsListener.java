package guru.sfg.brewery.security.listeners;

import guru.sfg.brewery.domain.security.LoginFailure;
import guru.sfg.brewery.domain.security.LoginSuccess;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.LoginFailureRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFailureBadCredentialsListener {

    private final LoginFailureRepository loginFailureRepository;
    private final UserRepository userRepository;

    @EventListener
    public void listen(AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent) {
        log.debug("Bad credentials were entered");


        if (authenticationFailureBadCredentialsEvent.getSource() instanceof UsernamePasswordAuthenticationToken) {

            LoginFailure.LoginFailureBuilder builder = LoginFailure.builder();
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authenticationFailureBadCredentialsEvent.getSource();

            if (token.getDetails() instanceof WebAuthenticationDetails) {

                WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) token.getDetails();
                log.debug("Ip adres is {}", webAuthenticationDetails.getRemoteAddress());
                builder.sourceIp(webAuthenticationDetails.getRemoteAddress());
            }

            if (token.getPrincipal() instanceof String) {
                String userInp = (String) token.getPrincipal();
                builder.username(userInp);
                log.debug("User {} provided bad credentials ", userInp);
                //
                Optional<User> byUsername = userRepository.findByUsername(userInp);
                byUsername.ifPresent(builder::user);


            }

            LoginFailure failure = builder.build();
            LoginFailure result = loginFailureRepository.save(failure);

            if (result.getUser() != null){

                lockUserAccount(result.getUser());

            }
            log.debug("Login failure was saved with id : {} and name {}", result.getId(), result.getUsername());


        }

    }

    private void lockUserAccount(User user) {

        List<LoginFailure> failures = loginFailureRepository.findAllByUserAndCreatedDateIsAfter(user,
               Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
        if (failures.size() > 3){
            log.debug("Locking user account {}", user.getUsername());
            user.setAccountNonLocked(false);
            userRepository.save(user);
        }
    }

}
