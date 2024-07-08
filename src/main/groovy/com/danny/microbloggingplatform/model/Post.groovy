package com.danny.microbloggingplatform.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "posts")
@CompoundIndex(name = "userId_createdAt_idx", def = "{'userId': 1, 'createdAt': -1}")
class Post {
    @Id
    ObjectId id
    ObjectId userId
    String content
    Date createdAt
    List<ObjectId> likes
    List<Comment> comments
}
