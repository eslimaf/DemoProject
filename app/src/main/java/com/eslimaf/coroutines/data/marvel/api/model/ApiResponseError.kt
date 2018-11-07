package com.eslimaf.coroutines.data.marvel.api.model

/**
 * Represent an error returned by the api
 * @param code ApiResponse error code
 * @param status Description of the error
 */
data class ApiResponseError(
        val code: Int,
        val status: String
)