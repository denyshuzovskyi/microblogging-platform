package com.danny.microbloggingplatform.repository

import com.danny.microbloggingplatform.model.Comment
import org.bson.types.ObjectId

interface CustomPostRepository {
    void like(ObjectId postId, ObjectId userId)
    void unlike(ObjectId postId, ObjectId userId)
    void comment(ObjectId postId, Comment comment)
}