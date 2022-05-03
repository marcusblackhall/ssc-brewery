package guru.sfg.brewery.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class UrlParametersAuthFilter extends SfgAuthFilter {
    public UrlParametersAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }


    public String getUser(HttpServletRequest request) {
        String user = request.getParameter("Api-Key");
        return user == null ? "" : user;
    }

    public String getPassword(HttpServletRequest request) {
        String password = request.getParameter("Api-Secret");
        return password == null ? "" : password;
    }


}
