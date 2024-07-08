package com.danny.microbloggingplatform.service

import com.danny.microbloggingplatform.model.Token
import com.danny.microbloggingplatform.repository.TokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TokenService {
    @Autowired
    TokenRepository tokenRepository

    Token issueToken(String username) {
        deleteToken(username)

        Token token = createToken(username)

        return tokenRepository.save(token)
    }

    void deleteToken(String username) {
        tokenRepository.deleteByUsername(username)
    }

    private static Token createToken(String username) {
        Token token = new Token()
        token.token = UUID.randomUUID().toString()
        token.username = username
        token.issuedAt = new Date()

        Calendar calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, 30)

        token.expiresAt = calendar.getTime()

        return token
    }
}
