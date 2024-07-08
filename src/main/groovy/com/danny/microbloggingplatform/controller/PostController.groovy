package com.danny.microbloggingplatform.controller

import com.danny.microbloggingplatform.dto.CommentCreationDTO
import com.danny.microbloggingplatform.dto.PostCreationDTO
import com.danny.microbloggingplatform.model.Post
import com.danny.microbloggingplatform.model.User
import com.danny.microbloggingplatform.service.PostService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController {
    @Autowired
    PostService postService

    @PostMapping
    ResponseEntity<Post> createPost(@AuthenticationPrincipal User authenticatedUser, @RequestBody PostCreationDTO postCreationDTO) {
        Post post = postService.create(authenticatedUser.id, postCreationDTO)
        return new ResponseEntity<>(post, HttpStatus.CREATED)
    }

    @PostMapping("/{postId}/like")
    ResponseEntity<Void> like(@PathVariable("postId") ObjectId postId, @AuthenticationPrincipal User authenticatedUser) {
        postService.like(postId, authenticatedUser.id)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{postId}/unlike")
    ResponseEntity<Void> unlike(@PathVariable("postId") ObjectId postId, @AuthenticationPrincipal User authenticatedUser) {
        postService.unlike(postId, authenticatedUser.id)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{postId}/comments")
    ResponseEntity<Void> createComment(@PathVariable("postId") ObjectId postId, @RequestBody CommentCreationDTO commentCreationDTO, @AuthenticationPrincipal User authenticatedUser) {
        postService.createComment(postId, commentCreationDTO, authenticatedUser.id)
        return ResponseEntity.ok().build()
    }
}
