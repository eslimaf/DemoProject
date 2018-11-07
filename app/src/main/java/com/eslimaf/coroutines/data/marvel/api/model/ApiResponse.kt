package com.eslimaf.coroutines.data.marvel.api.model

import com.google.gson.annotations.SerializedName

/** *DataWrapper (Marvel API)
 * Defines a wrapper object for the response
 * @param code The HTTP status code of the returned result
 * @param status A string description of the call status
 * @param dataContainer The results returned by the call
 * @param etag A digest value of the content
 * @param copyright The copyright notice for the returned result
 * @param attributionText The attribution notice for this result
 * @param attributionHTML An HTML representation of the attribution notice for this result
 */
data class ApiResponse<T>(
    val code: Int,
    val status: String,
    @SerializedName("data") val dataContainer: DataContainer<T>,
    val etag: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String
)