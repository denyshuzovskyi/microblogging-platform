package com.danny.microbloggingplatform.controller

import com.danny.microbloggingplatform.model.Post
import com.danny.microbloggingplatform.model.User
import com.danny.microbloggingplatform.service.PostService
import com.danny.microbloggingplatform.service.UserService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController {
    @Autowired
    UserService userService

    @Autowired
    PostService postService

    @GetMapping("/by-username/{username}")
    ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        User user = userService.getUserByUsername(username)
        return ResponseEntity.ok(user)
    }

    @PostMapping("/{userId}/follow")
    ResponseEntity<Void> follow(@AuthenticationPrincipal User authenticatedUser, @PathVariable("userId") ObjectId userIdToFollow) {
        userService.follow(authenticatedUser.id, userIdToFollow)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{userId}/unfollow")
    ResponseEntity<Void> unfollow(@AuthenticationPrincipal User authenticatedUser, @PathVariable("userId") ObjectId userIdToUnfollow) {
        userService.unfollow(authenticatedUser.id, userIdToUnfollow)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{userId}/posts")
    ResponseEntity<List<Post>> getUsersPosts(@PathVariable("userId") ObjectId userId) {
        List<Post> posts = userService.getUsersPosts(userId)
        return ResponseEntity.ok(posts)
    }

    @GetMapping("/feed")
    ResponseEntity<List<Post>> getUsersFeed(@AuthenticationPrincipal User authenticatedUser) {
        List<ObjectId> following = userService.getUsersFollowing(authenticatedUser.id)
        List<Post> posts = postService.getAllPost(following)
        return ResponseEntity.ok(posts)
    }
}