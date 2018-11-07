package com.eslimaf.coroutines.data.marvel.api.model.entity

import java.util.*

/** Character entity (Marvel API)
id (int, optional): The unique ID of the character resource.,
name (string, optional): The name of the character.,
description (string, optional): A short bio or description of the character.,
modified (Date, optional): The date the resource was most recently modified.,
resourceURI (string, optional): The canonical URL identifier for this resource.,
urls (Array[Url], optional): A set of public web site URLs for the resource.,
thumbnail (Image, optional): The representative image for this character.,
 */
data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val modified: Date,
    val resourceURI: String,
    val urls: List<Url>,
    val thumbnail: Image
):Comparable<Character>{
    override fun compareTo(other: Character): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}