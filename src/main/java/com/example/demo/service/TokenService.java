package com.example.demo.service;

import com.example.demo.model.Token;
import com.example.demo.repository.TokenRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenService {
    private TokenRepository tokenRepository;

    @Autowired
    public void setTokenRepository(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public void logout(String jwt) {
        Token token = tokenRepository.findByToken(jwt).orElseThrow(() -> new ObjectNotFoundException(Token.class, "Token not found"));
        token.setActive(false);
        tokenRepository.save(token);
    }
}
