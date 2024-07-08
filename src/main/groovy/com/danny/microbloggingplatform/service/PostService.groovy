package com.danny.microbloggingplatform.service

import com.danny.microbloggingplatform.dto.CommentCreationDTO
import com.danny.microbloggingplatform.dto.PostCreationDTO
import com.danny.microbloggingplatform.mapper.CommentMapper
import com.danny.microbloggingplatform.mapper.PostMapper
import com.danny.microbloggingplatform.model.Comment
import com.danny.microbloggingplatform.model.Post
import com.danny.microbloggingplatform.repository.PostRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class PostService {
    @Autowired
    PostMapper postMapper

    @Autowired
    CommentMapper commentMapper

    @Autowired
    PostRepository postRepository

    Post create(ObjectId userId, PostCreationDTO postCreationDTO) {
        Post post = postMapper.PostCreationDTOToPost(postCreationDTO)
        post.userId = userId
        post.createdAt = new Date()

        return postRepository.save(post)
    }

    void like(ObjectId postId, ObjectId userId) {
        postRepository.like(postId, userId)
    }

    void unlike(ObjectId postId, ObjectId userId) {
        postRepository.unlike(postId, userId)
    }

    void createComment(ObjectId postId, CommentCreationDTO commentCreationDTO, ObjectId userId) {
        Comment comment = commentMapper.CommentCreationDTOToComment(commentCreationDTO)
        comment.userId = userId
        comment.createdAt = new Date()

        postRepository.comment(postId, comment)
    }

    List<Post> getAllPost(List<ObjectId> userIds, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending())
        return postRepository.findAllByUserIdIn(userIds, pageRequest)
    }
}
