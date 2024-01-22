package com.example.demo.filter;

import com.example.demo.models.BlacklistToken;
import com.example.demo.repositories.BlacklistTokenRepository;
import com.example.demo.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private UserDetailsService userDetailsService;
    private BlacklistTokenRepository blacklistTokenRepository;


    @Autowired
    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService, BlacklistTokenRepository blacklistTokenRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.blacklistTokenRepository = blacklistTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            final String authorizationHeader = request.getHeader("Authorization");

            String username = null;
            String jwt = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = jwtService.getUsernameFromToken(jwt);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Check if the token is blacklisted
                BlacklistToken blacklistToken = blacklistTokenRepository.findByToken(jwt);
                if (blacklistToken != null && !blacklistToken.isActive()) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Access Denied: This token has been blacklisted.");
                    throw new ServletException("This token has been blacklisted.");
                }

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtService.validateToken(jwt)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }

            filterChain.doFilter(request, response);
        }
        catch (ExpiredJwtException e){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access Denied: JWT token has expired");
            return; // Stop further filter chain execution
        }

    }

}
