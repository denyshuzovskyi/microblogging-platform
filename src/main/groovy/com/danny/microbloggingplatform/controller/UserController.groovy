package com.danny.microbloggingplatform.controller

import com.danny.microbloggingplatform.model.Post
import com.danny.microbloggingplatform.model.User
import com.danny.microbloggingplatform.service.PostService
import com.danny.microbloggingplatform.service.UserService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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
    ResponseEntity<Page<Post>> getUsersPosts(
            @PathVariable("userId") ObjectId userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<Post> posts = userService.getUsersPosts(userId, page, size)
        return ResponseEntity.ok(posts)
    }

    @GetMapping("/feed")
    ResponseEntity<Page<Post>> getUsersFeed(
            @AuthenticationPrincipal User authenticatedUser,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        List<ObjectId> following = userService.getUsersFollowing(authenticatedUser.id)
        Page<Post> posts = postService.getAllPost(following, page, size)
        return ResponseEntity.ok(posts)
    }
}