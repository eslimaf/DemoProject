package com.eslimaf.coroutines.data.marvel.api.model.entity

/** TextObject entity (Marvel API)
type (string, optional): The canonical type of the text object (e.g. solicit text, preview text, etc.).,
language (string, optional): The IETF language tag denoting the language the text object is written in.,
text (string, optional): The text.
 */
data class TextObjects(
    val type: String,
    val language: String,
    val text: String
)