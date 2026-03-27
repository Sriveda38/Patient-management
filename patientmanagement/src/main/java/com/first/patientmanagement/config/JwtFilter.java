package com.first.patientmanagement.config;

import com.first.patientmanagement.service.JWTService;
import com.first.patientmanagement.service.PatientService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public  class JwtFilter extends OncePerRequestFilter {

    private final ApplicationContext context;

    private final JWTService jwtService;

    public JwtFilter(ApplicationContext context, JWTService jwtService) {
        this.context = context;
        this.jwtService = jwtService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return  path.equals("/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader);
        String token = null;
        String username = null;

        if(authHeader !=null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            System.out.println("Extracted Token: " + token);
            username = jwtService.extractUserName(token);
            System.out.println("Extracted Username/Email from Token: " + username);
        }

        if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null) {

            System.out.println("Loading user from DB...");

            UserDetails userDetails = context.getBean(PatientService.class).loadUserByUsername(username);
            System.out.println("UserDetails loaded: " + userDetails.getUsername());

            if(jwtService.validateToken(token, userDetails)) {
                System.out.println("Token is VALID");

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
            else {
                System.out.println("Token is INVALID");
            }
        }
        filterChain.doFilter(request,response);

    }
}
