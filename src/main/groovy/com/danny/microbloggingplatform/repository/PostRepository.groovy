package com.danny.microbloggingplatform.repository

import com.danny.microbloggingplatform.model.Post
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository extends MongoRepository<Post, ObjectId>, CustomPostRepository {
    Page<Post> findAllByUserId(ObjectId userId, Pageable pageable)
    Page<Post> findAllByUserIdIn(List<ObjectId> userIds, Pageable pageable)
}