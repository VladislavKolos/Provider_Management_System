package org.example.exception.handler

import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ProviderExceptionHandler {

    @Value("\${app.support.email}")
    lateinit var supportEmail: String

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<String> {
        logger.error("An unexpected error occurred: ${ex.message}", ex)

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An unexpected error occurred. Please try again later or contact support: $supportEmail")
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<String> {
        logger.error("Unexpected runtime exception occurred: ${ex.message}", ex)

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An unexpected error occurred. Please try again later or contact support: $supportEmail")
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<String> {
        logger.error("Validation failed during entity persistence: ${ex.message}", ex)

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("Data validation error. Please ensure all fields meet the required criteria and try again.")
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<String> {
        logger.error("Illegal state encountered: ${ex.message}", ex)

        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body("Access denied.")
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(ex: NoSuchElementException): ResponseEntity<String> {
        logger.error("No such element found: ${ex.message}", ex)

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body("The requested resource was not found. Please verify your input and try again.")
    }

    @ExceptionHandler(NullPointerException::class)
    fun handleNullPointerException(ex: NullPointerException): ResponseEntity<String> {
        logger.error("Null pointer exception occurred: ${ex.message}", ex)

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An internal error occurred. Please try again later.")
    }


}