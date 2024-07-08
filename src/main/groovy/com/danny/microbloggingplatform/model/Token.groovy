package com.danny.microbloggingplatform.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "tokens")
class Token {
    @Id
    String id
    @Indexed(unique = true)
    String token
    String username
    Date issuedAt
    @Indexed(expireAfterSeconds = 0)
    Date expiresAt
}
