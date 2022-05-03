package guru.sfg.brewery.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RestHeaderAuthFilter extends SfgAuthFilter {

    public RestHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }


    public String getPassword(HttpServletRequest httpServletRequest) {
        String secret = httpServletRequest.getHeader("Api-Secret");
        return secret == null ? "" : secret;

    }

    public String getUser(HttpServletRequest httpServletRequest) {

        String header = httpServletRequest.getHeader("Api-Key");
        return header == null ? "" : header;
    }




    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        if (log.isDebugEnabled()) {
            log.debug("Authentication request failed: " + failed.toString(), failed);
            log.debug("Updated SecurityContextHolder to contain null Authentication");
        }

        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());

//        this.rememberMeServices.loginFail(request, response);
//        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }


}
