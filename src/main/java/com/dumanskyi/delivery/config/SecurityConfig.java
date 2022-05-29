package com.dumanskyi.delivery.config;

import com.dumanskyi.delivery.entities.User;
import com.dumanskyi.delivery.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    public SecurityConfig(UserRepository userRepository, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        List<User> users = userRepository.findAll();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //.antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/anonymous/**","/login", "/registration", "/resources/**", "/css/**", "/welcome", "/", "/createCustomer").permitAll()
                //.antMatchers().permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                //.loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/welcome", true)
                .failureUrl("/login?error=true")
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .logoutSuccessUrl("/welcome")
                .deleteCookies("JSESSIONID");
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
