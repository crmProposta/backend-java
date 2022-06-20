package com.proposta.crm.advice

import com.proposta.crm.exception.IncorrectCredentialsException
import com.proposta.crm.handler.ResponseHandler
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class RuntimeExceptionAdvice {

    @ExceptionHandler(IncorrectCredentialsException::class)
    fun handleIncorrectCredentialsException(e: IncorrectCredentialsException): ResponseEntity<Any> {
        return ResponseEntity.badRequest().body(
            ResponseHandler.Error("incorrectCredentials", e.message)
        )

    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUsernameNotFoundException(e: UsernameNotFoundException): ResponseEntity<Any> {
        return ResponseEntity.badRequest().body(
            ResponseHandler.Error("badCredentials", e.localizedMessage)
        )
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(e: BadCredentialsException): ResponseEntity<Any> {
        return ResponseEntity.badRequest().body(
            ResponseHandler.Error("badCredentials", e.localizedMessage)
        )
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<Any> {
        val stringBuilder = StringBuilder()
        e.constraintViolations.forEach {
            stringBuilder.append(it.message + ", ")
        }
        stringBuilder.removeRange(stringBuilder.length - 3, stringBuilder.length)

        return ResponseEntity.ok().body(
            ResponseHandler.Error("constraintViolations", "violations: $stringBuilder")
        )
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(e: DataIntegrityViolationException): ResponseEntity<Any> {
        return ResponseEntity.ok().body(
            ResponseHandler.Error("constraintViolations", "constraint error")
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpmessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<Any> {
        e.printStackTrace()
        return ResponseEntity.badRequest().body(
            ResponseHandler.Error("httpMessageNotReadable", "We can read what you send to us")
        )
    }


    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException): ResponseEntity<Any> {
        e.printStackTrace()
        return ResponseEntity.internalServerError().body(
            ResponseHandler.Error("internalServerError", e.localizedMessage)
        )
    }



}