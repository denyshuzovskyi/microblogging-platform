package com.danny.microbloggingplatform.controller

import com.danny.microbloggingplatform.dto.UserCreationDTO
import com.danny.microbloggingplatform.dto.UserCredentialsDTO
import com.danny.microbloggingplatform.model.Token
import com.danny.microbloggingplatform.model.User
import com.danny.microbloggingplatform.service.AuthService
import com.danny.microbloggingplatform.service.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController {
    @Autowired
    AuthService authService

    @Autowired
    TokenService tokenService

    @PostMapping("/sign-up")
    ResponseEntity<Token> signUp(@RequestBody UserCreationDTO userCreationDTO) {
        return ResponseEntity.ok(authService.signUp(userCreationDTO))
    }

    @PostMapping("/log-in")
    ResponseEntity<Token> logIn(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        return ResponseEntity.ok(authService.authenticate(userCredentialsDTO))
    }

    @DeleteMapping("/log-out")
    ResponseEntity<Void> logOut(@AuthenticationPrincipal User authenticatedUser) {
        tokenService.deleteToken(authenticatedUser.username)
        return new ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
