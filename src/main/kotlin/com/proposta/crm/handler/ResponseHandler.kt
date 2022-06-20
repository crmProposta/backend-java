package com.proposta.crm.handler

import com.proposta.crm.model.ResponseStatusEnum
import org.springframework.http.HttpStatus

sealed class ResponseHandler<out T> {

    data class Success<T: Any>(
        val status: ResponseStatusEnum,
        val codeMessage: String,
        val data: T?
    ) : ResponseHandler<T>()

    data class Error(
        val codeMessage: String,
        val message: String = "",
        val status: ResponseStatusEnum = ResponseStatusEnum.ERROR,
    ) : ResponseHandler<Nothing>()
}

