package com.myecom.admin.admin.Config;

import com.myecom.gallery.gallery.Model.Admin;
import org.hibernate.annotations.DialectOverride;
//import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AdminSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new AdminDetailsService();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    protected  void configure(AuthenticationManagerBuilder builder) throws  Exception
    {
        builder.authenticationProvider(daoAuthenticationProvider());
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)  throws  Exception{
        http
                .csrf().disable()
                .authorizeHttpRequests(auth ->
                            auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                    .requestMatchers("/src/main/resources/**").permitAll()
                                    .requestMatchers("/images/**", "/js/**", "/css/**", "/scss/**","/fonts/**").permitAll()
                                    .requestMatchers("/admin/**").hasRole("ADMIN")
                                    .requestMatchers("/forgot-password","/register","/register-new").permitAll()
                                    .anyRequest().authenticated()
                        )

                .formLogin(login ->
                        login.loginPage("/login")
                                .loginProcessingUrl("/do-login")
                                .defaultSuccessUrl("/index",true)
                                .permitAll()
                )
                .logout(logout ->
                        logout.invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                );
return http.build();
    }

}
