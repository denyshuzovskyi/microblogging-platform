package com.danny.microbloggingplatform.repository

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import com.danny.microbloggingplatform.model.User

@Repository
class CustomUserRepositoryImpl implements CustomUserRepository{
    @Autowired
    private MongoTemplate mongoTemplate

    @Override
    void addToFollowing(ObjectId userId, ObjectId userIdToFollow) {
        Query query = new Query(Criteria.where("_id").is(userId))
        Update update = new Update().addToSet("following", userIdToFollow)
        mongoTemplate.updateFirst(query, update, User.class)
    }

    @Override
    void addToFollowers(ObjectId userId, ObjectId followerUserId) {
        Query query = new Query(Criteria.where("_id").is(userId))
        Update update = new Update().addToSet("followers", followerUserId)
        mongoTemplate.updateFirst(query, update, User.class)
    }

    @Override
    void pullFromFollowing(ObjectId userId, ObjectId userIdToUnfollow) {
        Query query = new Query(Criteria.where("_id").is(userId))
        Update update = new Update().pull("following", userIdToUnfollow)
        mongoTemplate.updateFirst(query, update, User.class)
    }

    @Override
    void pullFromFollowers(ObjectId userId, ObjectId followerUserId) {
        Query query = new Query(Criteria.where("_id").is(userId))
        Update update = new Update().pull("followers", followerUserId)
        mongoTemplate.updateFirst(query, update, User.class)
    }
}
