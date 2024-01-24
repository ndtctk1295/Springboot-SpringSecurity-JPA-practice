package com.example.demo.config;

//import com.example.demo.handler.CustomAuthenticationFailureHandler;
//import com.example.demo.handler.CustomAuthenticationSuccessHandler;

import com.example.demo.filter.JwtAuthFilter;
import com.example.demo.handler.CustomLogoutHandler;
import com.example.demo.handler.CustomLogoutSuccessHandler;
import com.example.demo.repositories.BlacklistTokenRepository;
import com.example.demo.services.CustomerDetailsService;
import com.example.demo.services.JwtService;
import com.example.demo.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
//@EnableMethodSecurity
public class SecurityConfig {

    private final CustomerDetailsService customerDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final BlacklistTokenRepository blacklistTokenRepository;
    private final TokenService tokenService;
//    private final RedisService redisService;

    public SecurityConfig(CustomerDetailsService customerDetailsService, PasswordEncoder passwordEncoder, JwtService jwtService, BlacklistTokenRepository blacklistTokenRepository, TokenService tokenService) {
        this.customerDetailsService = customerDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;

        this.blacklistTokenRepository = blacklistTokenRepository;
        this.tokenService = tokenService;
    }


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtService, customerDetailsService, blacklistTokenRepository);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .cors((cors) -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS )
                )
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers( new AntPathRequestMatcher("/js/**")).permitAll()
                        .requestMatchers( new AntPathRequestMatcher("/api/v1/public/user/**")).permitAll()

                        .requestMatchers( new AntPathRequestMatcher("/api/v1/products/**", "GET")).permitAll()
                        .requestMatchers( new AntPathRequestMatcher("/api/v1/products/**")).hasRole("ADMIN")

                        .requestMatchers( new AntPathRequestMatcher("/api/v1/customers/**")).hasRole("ADMIN")

                        .requestMatchers( new AntPathRequestMatcher("/api/v1/ordersItem/get-all")).hasRole("ADMIN")
                        .requestMatchers( new AntPathRequestMatcher("/api/v1/orders/get-all")).hasRole("ADMIN")


                        .requestMatchers( new AntPathRequestMatcher("/api/v1/coupon/get-all")).hasRole("ADMIN") // need to adjust
                        .requestMatchers( new AntPathRequestMatcher("/api/v1/coupon/insert")).hasRole("ADMIN")
                        .requestMatchers( new AntPathRequestMatcher("/api/v1/coupon/delete")).hasRole("ADMIN")

                        .requestMatchers( new AntPathRequestMatcher("/api/v1/public/user/**", "POST")).permitAll()

                        .requestMatchers( new AntPathRequestMatcher("/api/v1/admin/**")).hasRole("ADMIN")
                        .requestMatchers( new AntPathRequestMatcher("/admin/**")).permitAll()

                        .requestMatchers( new AntPathRequestMatcher("/api/v1/secured/**")).authenticated()

                        .anyRequest().authenticated()
                )
                .logout((logout) -> logout
                        .logoutUrl("/api/v1/public/user/logout")
                        .addLogoutHandler(new CustomLogoutHandler(tokenService)) // add your custom logout handler
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                        .permitAll()
                )
                .formLogin((formLogin) -> formLogin
                        .loginPage("/internal-view/admin/login")
//                        .loginProcessingUrl("/api/v1/admin/login")
                        .defaultSuccessUrl("/dashboard.html", true) // Redirect to the admin dashboard upon successful login
                        .failureUrl("/login?error=true") // Redirect back to the login page with an error
                        .permitAll())
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://localhost:3001" )); // Or your client's origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
