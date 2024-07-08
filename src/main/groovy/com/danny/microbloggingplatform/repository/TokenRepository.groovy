package com.danny.microbloggingplatform.repository

import com.danny.microbloggingplatform.model.Token
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface TokenRepository extends MongoRepository<Token, ObjectId> {
    Optional<Token> findByToken(String token)
    Optional<Token> findByUsername(String username)
    void deleteByUsername(String username)
}