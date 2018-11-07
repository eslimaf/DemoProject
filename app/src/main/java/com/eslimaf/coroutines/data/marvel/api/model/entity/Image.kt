package com.eslimaf.coroutines.data.marvel.api.model.entity

/*
Image
path (string, optional): The directory path of to the image.,
extension (string, optional): The file extension for the image.
 */
data class Image(
    val path: String,
    val extension: String
) {
    fun getStandardUrl(): String {
        return "$path/standard_fantastic.$extension"
    }

    fun getPortraitUrl(): String {
        return "$path/portrait_incredible.$extension"
    }
}