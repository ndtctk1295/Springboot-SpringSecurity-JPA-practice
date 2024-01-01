package com.example.demo.config;

//import com.example.demo.handler.CustomAuthenticationFailureHandler;
//import com.example.demo.handler.CustomAuthenticationSuccessHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
//    private final PasswordEncoder passwordEncoder;

//    @Autowired
//    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        this.userDetailsService = userDetailsService;
//        this.passwordEncoder = passwordEncoder;
//    }
//    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    private static final String[] AUTH_WHITELIST = {"/", "/home", "/api/v1/**"};

//    public SecurityConfig(com.example.demo.services.CustomerDetailsService customerDetailsService) {
//        this.customerDetailsService = customerDetailsService;
//    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager customAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
              .cors((cors) -> cors
                      .disable()

              )
              .csrf((csrf) -> csrf.disable())
              .sessionManagement((session) -> session
                      .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
              )
              .authorizeHttpRequests((requests) -> requests
//                              .requestMatchers( new AntPathRequestMatcher("/**")).permitAll()
                              .requestMatchers( new AntPathRequestMatcher("/home")).permitAll()
                              .requestMatchers( new AntPathRequestMatcher("/api/v1/customers/**")).hasRole("ADMIN")
                              .requestMatchers( new AntPathRequestMatcher("/api/v1/public/user/**", "POST")).permitAll()
                              .requestMatchers( new AntPathRequestMatcher("/api/v1/admin/**", "POST")).permitAll()
                              .requestMatchers( new AntPathRequestMatcher("/api/v1/secured/**")).authenticated()
//                              .requestMatchers("/api/v1/public/user/login", "/api/v1/public/user/register", "/api/v1/public/user/logout").permitAll()
                      .anyRequest().authenticated()
              )
//              .formLogin((form) -> form
//                      .loginProcessingUrl("/api/v1/public/user/login")
////                              .successHandler(new CustomAuthenticationSuccessHandler())
////                              .failureHandler(new CustomAuthenticationFailureHandler())
//              )
                .logout((logout) -> logout
                        .logoutUrl("/api/v1/user/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/api/v1/products/get-all")
                        .permitAll()
                );
      return http.build();
   }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder);
//
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(passwordEncoder.encode("password"))
//                .authorities("ROLE_USER");
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }





}
