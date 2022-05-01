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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder(10);
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
                .password("$2a$10$bWVuFZZLnElc9mdw01bkaewDD3tXOOrldxk28xgWl2f8xyRSBfE3O") // noop ensures using an encoder
                .roles("ADMIN")
                .and()
                .withUser("user")
//                .password("{noop}password")
                .password("$2a$10$VVewZvXmgyp7EX/iLPOuJeL.SL1CdXr/fkqFbcF87p0BSB8aJBCf2")
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
