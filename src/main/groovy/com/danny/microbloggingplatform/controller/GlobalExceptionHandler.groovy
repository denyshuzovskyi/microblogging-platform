package com.danny.microbloggingplatform.controller

import com.danny.microbloggingplatform.service.exception.PasswordDoesNotMatchException
import com.danny.microbloggingplatform.service.exception.UserNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class)

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<Void> handleUserNotFoundException(UserNotFoundException e) {
        logger.error("Caught exception", e)
        return new ResponseEntity<>(HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(PasswordDoesNotMatchException.class)
    ResponseEntity<Void> handlePasswordDoesNotMatchException(PasswordDoesNotMatchException e) {
        logger.error("Caught exception", e)
        return new ResponseEntity<>(HttpStatus.FORBIDDEN)
    }
}
