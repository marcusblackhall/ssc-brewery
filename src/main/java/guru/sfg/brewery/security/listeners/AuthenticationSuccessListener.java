package guru.sfg.brewery.security.listeners;

import guru.sfg.brewery.domain.security.LoginSuccess;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.LoginSuccessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationSuccessListener {

    private final LoginSuccessRepository loginSuccessRepository;

    @EventListener
    public void listen(AuthenticationSuccessEvent authenticationSuccessEvent) {
        log.debug("User logged in oke");

        if (authenticationSuccessEvent.getSource() instanceof UsernamePasswordAuthenticationToken){

            LoginSuccess.LoginSuccessBuilder builder = LoginSuccess.builder();
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authenticationSuccessEvent.getSource();
            if (token.getPrincipal() instanceof User) {
                User user = (User) token.getPrincipal();
                builder.user(user);
                log.debug("User {} logged in succesfully ", user.getUsername());
            }
            if (token.getDetails() instanceof WebAuthenticationDetails){

              WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) token.getDetails();
              log.debug("Ip adres is {}",webAuthenticationDetails.getRemoteAddress());
              builder.sourceIp(webAuthenticationDetails.getRemoteAddress());
            }

            LoginSuccess loginSuccess = loginSuccessRepository.save(LoginSuccess.builder().build());
            log.debug("Login success saved. Id : {}",loginSuccess.getId());
        }
    }

}
