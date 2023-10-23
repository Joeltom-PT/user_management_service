package com.web.user_management.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public SecurityConfig(SuccessHandler successHandler, UserDetailsService userDetailsService) {
        this.successHandler = successHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    SuccessHandler successHandler;

    final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {

        http.
                authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                .requestMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated()

                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .successHandler(successHandler)
                                .permitAll()
                ).sessionManagement(sessionManagement -> sessionManagement
                        .maximumSessions(1)
                        .expiredUrl("/login")
                        .maxSessionsPreventsLogin(true)


                ).logout(logout ->
                        logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                );
        return http.build();
    }

    protected void Config(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
