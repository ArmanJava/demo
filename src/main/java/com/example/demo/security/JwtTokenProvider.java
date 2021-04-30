package com.example.demo.security;


import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    private TokenRepository tokenRepository;
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setTokenRepository(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    @Transactional
    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        String jwt = Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        User user = userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(() -> new ObjectNotFoundException(User.class, "User not found"));
        Token token = new Token();
        token.setToken(jwt);
        token.setUser(user);
        token.setActive(true);
        tokenRepository.save(token);

        return jwt;
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    @Transactional
    public boolean validateToken(String authToken) {

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            Token token = tokenRepository.findByToken(authToken).orElseThrow(() -> new ObjectNotFoundException(Token.class, "Token not found"));
            if (token.isActive()) {
                return true;
            }
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        } catch (ObjectNotFoundException ex) {
            log.error("Token Not Found");
        }
        return false;
    }
}
