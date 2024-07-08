package com.danny.microbloggingplatform.service.exception

class PasswordDoesNotMatchException extends RuntimeException{
    PasswordDoesNotMatchException(String message) {
        super(message)
    }

    PasswordDoesNotMatchException(String message, Throwable cause) {
        super(message, cause)
    }
}
