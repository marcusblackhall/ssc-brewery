package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize ->
                        authorize.antMatchers("/","/login","/resources/**","/webjars/**","/beers/find")
                                .permitAll()
                                .antMatchers(HttpMethod.GET,"/api/v1/beer/**").permitAll()
                                .mvcMatchers(HttpMethod.GET,"/api/v1/beerUpc/{upc}").permitAll()

                        )

        .authorizeRequests()

                .anyRequest().authenticated().and().formLogin().and().httpBasic();


    }

    /**
     * Override method to configure UserDetailsService with a fluent API
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("marcus")
                .password("{noop}marcus") // noop ensures using an encoder
                .roles("ADMIN")
                .and()
                .withUser("user")
//                .password("{noop}password")
                .password("password")
                .roles("USER")
                .and()
                .withUser("scott")
//                .password("{noop}tiger")
                .password("tiger")
                .roles("CUSTOMER")
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
