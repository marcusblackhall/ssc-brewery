package guru.sfg.brewery.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

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


}
