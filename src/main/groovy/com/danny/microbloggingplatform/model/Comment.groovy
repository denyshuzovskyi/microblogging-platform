package com.danny.microbloggingplatform.model

import org.bson.types.ObjectId

class Comment {
    ObjectId userId
    String content
    Date createdAt
}
