package com.danny.microbloggingplatform.service.exception

class UserNotFoundException extends RuntimeException{
    UserNotFoundException(String message) {
        super(message)
    }

    UserNotFoundException(String message, Throwable cause) {
        super(message, cause)
    }
}
