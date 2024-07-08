package com.danny.microbloggingplatform.service

import com.danny.microbloggingplatform.model.Post
import com.danny.microbloggingplatform.model.User
import com.danny.microbloggingplatform.repository.PostRepository
import com.danny.microbloggingplatform.repository.UserRepository
import com.danny.microbloggingplatform.service.exception.UserNotFoundException
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    UserRepository userRepository

    @Autowired
    PostRepository postRepository

    User getUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", username)))
    }

    void follow(ObjectId userId, ObjectId userIdToFollow) {
        userRepository.addToFollowing(userId, userIdToFollow)
        userRepository.addToFollowers(userIdToFollow, userId)
    }

    void unfollow(ObjectId userId, ObjectId userIdToUnfollow) {
        userRepository.pullFromFollowing(userId, userIdToUnfollow)
        userRepository.pullFromFollowers(userIdToUnfollow, userId)
    }

    List<Post> getUsersPosts(ObjectId userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending())
        return postRepository.findAllByUserId(userId, pageRequest)
    }

    List<ObjectId> getUsersFollowing(ObjectId userId) {
        Optional<User> user = userRepository.findById(userId)
        if (user.isPresent()) {
            return user.get().following
        }
        return Collections.<ObjectId>emptyList()
    }
}
