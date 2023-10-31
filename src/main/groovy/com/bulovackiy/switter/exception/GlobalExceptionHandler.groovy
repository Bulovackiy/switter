package com.bulovackiy.switter.exception

import com.bulovackiy.switter.model.dto.ErrorDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorDTO> handleException(Exception exception) {
        def error = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage())
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ErrorDTO> handleNotFoundException(NotFoundException exception) {
        def error = new ErrorDTO(HttpStatus.NOT_FOUND.value(), exception.getMessage())
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<ErrorDTO> handleValidationException(ValidationException exception) {
        def error = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage())
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<ErrorDTO> handleAuthenticationException(AuthenticationException exception) {
        def error = new ErrorDTO(HttpStatus.UNAUTHORIZED.value(), exception.getMessage())
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(ForbiddenException.class)
    ResponseEntity<ErrorDTO> handleForbiddenException(ForbiddenException exception) {
        def error = new ErrorDTO(HttpStatus.FORBIDDEN.value(), exception.getMessage())
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN)
    }
}
