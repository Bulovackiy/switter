package com.bulovackiy.switter.service

import com.bulovackiy.switter.repository.TokenRepository
import com.bulovackiy.switter.repository.model.Token
import org.springframework.stereotype.Service

@Service
class TokenService {

    private TokenRepository tokenRepository

    TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository
    }

    boolean addToken(String username, String token) {
        def tokenEntity = new Token()
        tokenEntity.username = username
        tokenEntity.token = token
        tokenEntity.createDate = new Date()

        def result = tokenRepository.findByUsername(username)
        if (result == null) {
            result = tokenRepository.save(tokenEntity)
        } else {
            result.token = token
            tokenRepository.save(result)
        }

        return result != null
    }

    void removeToken(String username) {
        def entity = tokenRepository.findByUsername(username)
        if (entity != null) {
            tokenRepository.delete(entity)
        }
    }

    boolean tokenExists(String username, String token) {
        return tokenRepository.existsByUsernameAndToken(username, token)
    }
}
