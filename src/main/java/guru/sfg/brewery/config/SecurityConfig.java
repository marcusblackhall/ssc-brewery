package guru.sfg.brewery.config;

import guru.sfg.brewery.security.RestHeaderAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager){

        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(
                new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);

        return  filter;

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        PasswordEncoder delegatingPasswordEncoder = MarcusEncoderFactories.createDelegatingPasswordEncoder();
        return delegatingPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(restHeaderAuthFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
        ;

        http
                .authorizeRequests(authorize ->
                        authorize.antMatchers("/", "/login", "/resources/**", "/webjars/**", "/beers/find")
                                .permitAll()
                                .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                                .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll()

                )

                .authorizeRequests()

                .anyRequest().authenticated().and().formLogin().and().httpBasic();


    }

    /**
     * Override method to configure UserDetailsService with a fluent API
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("marcus")
                .password("{bcrypt}$2a$10$bWVuFZZLnElc9mdw01bkaewDD3tXOOrldxk28xgWl2f8xyRSBfE3O") // noop ensures using an encoder
                .roles("ADMIN")
                .and()
                .withUser("user")
//                .password("{noop}password")
                .password("{bcrypt}$2a$10$en/GyDVUZpPH7wgUtL3nIejn7YP8t3JG7kGDhIJyr51URmUG.5v7.")
                .roles("USER")
                .and()
                .withUser("scott")
                .password("{bcrypt15}$2a$15$ATC4mZEBReUhRdpCJA8zne0sbIUnLBgKFOK5AueQtkhwDV5Od2e9u")
                .roles("ADMIN")
        ;

    }



//    @Override
//    @Bean
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//
//        UserDetails userDetails = User.withDefaultPasswordEncoder()
//             .username("user")
//                .password("password")
//                .roles("USER").build();
//
//        UserDetails userDetails2 = User.withDefaultPasswordEncoder()
//                .username("marcus")
//                .password("marcus")
//                .roles("ADMIN").build();
//
//
//        return new InMemoryUserDetailsManager(userDetails,userDetails2);
//    }
}
