package com.danny.microbloggingplatform.service

import com.danny.microbloggingplatform.dto.UserCreationDTO
import com.danny.microbloggingplatform.dto.UserCredentialsDTO
import com.danny.microbloggingplatform.mapper.UserMapper
import com.danny.microbloggingplatform.model.Token
import com.danny.microbloggingplatform.model.User
import com.danny.microbloggingplatform.repository.UserRepository
import com.danny.microbloggingplatform.service.exception.PasswordDoesNotMatchException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService {
    @Autowired
    UserMapper userMapper

    @Autowired
    PasswordEncoder passwordEncoder

    @Autowired
    UserRepository userRepository

    @Autowired
    TokenService tokenService

    Token signUp(UserCreationDTO userCreationDTO) {
        User user = userMapper.userCreationDTOToUser(userCreationDTO)
        user.password = passwordEncoder.encode(userCreationDTO.password)

        user = userRepository.save(user)

        return tokenService.issueToken(user.username)
    }

    Token authenticate(UserCredentialsDTO userCredentialsDTO) {
        User user = userRepository.findByUsername(userCredentialsDTO.username).orElseThrow(() -> new NoSuchElementException("Username does not exist"))
        if (passwordEncoder.matches(userCredentialsDTO.password, user.password)) {
            return tokenService.issueToken(user.username)
        }
        throw new PasswordDoesNotMatchException("Password does not match")
    }
}
