package com.library.Library.config;

import com.library.Library.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig   {

    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/**").hasAuthority("admin")
                        .requestMatchers("/available_books/**").hasAuthority("admin")
                        .requestMatchers("/books").authenticated()  // All authenticated users
                        .anyRequest().permitAll() // Other routes are public
                )
                .formLogin(login ->
                        login
                                .usernameParameter("email")


                                .permitAll()
                                .defaultSuccessUrl("/books")
                )
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll());

        return http.build();
    }


}
