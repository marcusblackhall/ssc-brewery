package guru.sfg.brewery.security.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationSuccessListener {

    @EventListener
    public void listen(AuthenticationSuccessEvent authenticationSuccessEvent){
        log.debug("User logged in oke");
    }

}
