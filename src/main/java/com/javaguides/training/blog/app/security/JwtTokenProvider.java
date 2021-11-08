package com.javaguides.training.blog.app.security;

import com.javaguides.training.blog.app.exception.BlogApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtsecret}")
    String jwtSecret;

    @Value("${app.jwtexpirationdate}")
    int jwtExpirationDateInMs;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date date = new Date();
        Date expirationDate = new Date(date.getTime() + jwtExpirationDateInMs);
        return Jwts.builder().setSubject(username)
                .setIssuedAt(new Date()).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (SignatureException ex) {
            throw new BlogApiException("Invalid jwt signature",HttpStatus.BAD_REQUEST);
        } catch (MalformedJwtException ex) {
            throw new BlogApiException("Malformd Jwt",HttpStatus.BAD_REQUEST);
        } catch (ExpiredJwtException ex) {
            throw new BlogApiException("Expired Jwt",HttpStatus.BAD_REQUEST);
        } catch (UnsupportedJwtException ex) {
            throw new BlogApiException("Unsupported Jwt",HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException ex) {
            throw new BlogApiException("Invalid Jwt",HttpStatus.BAD_REQUEST);
        }
        return Boolean.TRUE;
    }
}

