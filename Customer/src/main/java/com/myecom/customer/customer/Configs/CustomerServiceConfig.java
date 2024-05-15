package com.myecom.customer.customer.Configs;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class CustomerServiceConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomerDetailService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    public void configure(AuthenticationManagerBuilder builder) {
        builder.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception
    {
        http.csrf().disable()
                .authorizeHttpRequests(auth ->
                                auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                        .requestMatchers("/*","/product-detail/**").permitAll()
                                        .requestMatchers("/src/main/resources/**").permitAll()
                                        .requestMatchers("/images/**", "/js/**", "/css/**", "/scss/**","/fonts/**").permitAll()
                                        .requestMatchers("/customer/**","/find-products/**").hasAuthority("CUSTOMER")
                                        .anyRequest().authenticated()
                        )
                .formLogin(login ->
                        login.loginPage("/login")
                                .loginProcessingUrl("/do-login")
                                .defaultSuccessUrl("/index",true)
                                .permitAll()
                        )
                .logout( logout ->
                            logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                    .clearAuthentication(true)
                                    .invalidateHttpSession(true)
                                    .logoutSuccessUrl("/login?logout")
                                    .permitAll()
                );
        return  http.build();
    }
}

