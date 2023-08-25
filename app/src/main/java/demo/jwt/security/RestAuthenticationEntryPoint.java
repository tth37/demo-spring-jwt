package demo.jwt.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("restAuthenticationEntryPoint")
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        System.out.println("authException: " + authException);

        System.out.println(response.getStatus());

        if (response.getStatus() == HttpServletResponse.SC_EXPECTATION_FAILED) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            response.getWriter().write("Forbidden: not privileged");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

    }
}