package guru.sfg.brewery.config;

import guru.sfg.brewery.security.google.Google2faFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by jt on 6/13/20.
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PersistentTokenRepository persistentTokenRepository;

    private final Google2faFilter google2faFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(google2faFilter, SessionManagementFilter.class);

        http.cors().and()
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/h2-console/**").permitAll() //do not use in production!
                        .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                )
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin(loginConfigurer -> loginConfigurer.loginProcessingUrl("/login")
                        .loginPage("/")
                        .successForwardUrl("/")
                        .defaultSuccessUrl("/")
                        .failureUrl("/?error"))
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/?logout")

                        .permitAll())
                .httpBasic()
                .and().csrf()
                .ignoringAntMatchers("/h2-console/**", "/api/**")
                .and()
//                .rememberMe().key("sfg-key").userDetailsService(userDetailsService);
                .rememberMe().tokenRepository(persistentTokenRepository)
                .userDetailsService(userDetailsService);


        //h2 console config
        http.headers().frameOptions().sameOrigin();
    }


    // @Override
    //   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // auth.userDetailsService(this.jpaUserDetailsService).passwordEncoder(passwordEncoder());

//        auth.inMemoryAuthentication()
//                .withUser("spring")
//                .password("{bcrypt}$2a$10$7tYAvVL2/KwcQTcQywHIleKueg4ZK7y7d44hKyngjTwHCDlesxdla")
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password("{sha256}1296cefceb47413d3fb91ac7586a4625c33937b4d3109f5a4dd96c79c46193a029db713b96006ded")
//                .roles("USER");
//
//        auth.inMemoryAuthentication().withUser("scott").password("{bcrypt15}$2a$15$baOmQtw8UqWZRDQhMFPFj.xhkkWveCTQHe4OBdr8yw8QshejiSbI6").roles("CUSTOMER");
    //  }

    //    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("spring")
//                .password("guru")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }


}