package demo.jwt.security;

import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
public class JwtUtils {

    private static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .sign(HMAC512(secret.getBytes()));
    }

    public String getEmailFromJwtToken(String token) {
        return JWT.require(HMAC512(secret.getBytes()))
                .build()
                .verify(token)
                .getSubject();
    }
}
