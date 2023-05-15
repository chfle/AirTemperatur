package org.lehnert.AirTemperature.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration
 */
@EnableWebSecurity
@Configuration
@EnableMethodSecurity()
@AllArgsConstructor
public class WebServerConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // TODO: login without disable
        http.csrf().disable();
        http.cors();

        http.authorizeHttpRequests()
                .requestMatchers("/js/***", "/css/**", "/image/**").permitAll()
                .anyRequest().authenticated();

        http
                .formLogin()
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
                // TokenRepository should be used later
                .key("someToken");

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     *  memory users
     */
    @Bean
    UserDetailsManager userDetailsManager() {
       var inMem = new InMemoryUserDetailsManager();

        inMem.createUser(User.withUsername("chris").password("chris").authorities("Admin").build());

        return inMem;
    }
}
