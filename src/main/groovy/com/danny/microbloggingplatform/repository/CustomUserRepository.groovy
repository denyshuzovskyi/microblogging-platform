package com.danny.microbloggingplatform.repository

import org.bson.types.ObjectId

interface CustomUserRepository {
    void addToFollowing(ObjectId userId, ObjectId userIdToFollow);
    void addToFollowers(ObjectId userId, ObjectId followerUserId);
    void pullFromFollowing(ObjectId userId, ObjectId userIdToUnfollow);
    void pullFromFollowers(ObjectId userId, ObjectId followerUserId);
}