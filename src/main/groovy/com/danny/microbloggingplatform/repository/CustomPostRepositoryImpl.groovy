package com.danny.microbloggingplatform.repository

import com.danny.microbloggingplatform.model.Comment
import com.danny.microbloggingplatform.model.Post
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class CustomPostRepositoryImpl implements CustomPostRepository{
    @Autowired
    private MongoTemplate mongoTemplate

    @Override
    void like(ObjectId postId, ObjectId userId) {
        Query query = new Query(Criteria.where("_id").is(postId))
        Update update = new Update().addToSet("likes", userId)
        mongoTemplate.updateFirst(query, update, Post.class)
    }

    @Override
    void unlike(ObjectId postId, ObjectId userId) {
        Query query = new Query(Criteria.where("_id").is(postId))
        Update update = new Update().pull("likes", userId)
        mongoTemplate.updateFirst(query, update, Post.class)
    }

    @Override
    void comment(ObjectId postId, Comment comment) {
        Query query = new Query(Criteria.where("_id").is(postId))
        Update update = new Update().addToSet("comments", comment)
        mongoTemplate.updateFirst(query, update, Post.class)
    }
}
