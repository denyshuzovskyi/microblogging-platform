package com.danny.microbloggingplatform.repository

import com.danny.microbloggingplatform.model.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository extends MongoRepository<User, ObjectId>, CustomUserRepository{
    Optional<User> findByUsername(String username)
}