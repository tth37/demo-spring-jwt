package demo.jwt.security;

import demo.jwt.student.StudentUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;


import java.awt.*;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private StudentUserDetailsService studentUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        System.out.println("Doing filter");


        final String requestTokenHeader = request.getHeader("Authorization");

        String email = null;
        String jwtToken = null;

        // JWT Token is in the form "Bearer token"
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                email = jwtUtils.getEmailFromJwtToken(jwtToken);

                System.out.println("Email: " + email);

                if (email != null) {
                    System.out.println("Extracted email: " + email);

                    UserDetails userDetails = studentUserDetailsService.loadUserDetailsByEmail(email);

                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(token);

                    System.out.println("Email: " + email);
                    System.out.println("Roles: " + token.getAuthorities());
                } else {
                    System.out.println("Credentials not found");
                    response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
                    response.getWriter().write("Unauthorized: credentials not found");
                }

            } catch (Exception e) {
                System.out.println("Invalid token");

                response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
                response.getWriter().write("Unauthorized: invalid token");
            }
        } else {
            System.out.println("Credentials not found");
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            response.getWriter().write("Unauthorized: credentials not found");
        }

        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/security/hello") || request.getServletPath().equals("/security/login");
    }
}
