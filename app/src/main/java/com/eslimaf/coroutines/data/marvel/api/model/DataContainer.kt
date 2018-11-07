package com.eslimaf.coroutines.data.marvel.api.model

/** *DataContainer (Marvel API)
 * @param offset The requested offset (skipped results) of the call
 * @param limit The requested result limit
 * @param total The total number of results available
 * @param count The total number of results returned by this call
 * @param results List[entity type]
 */
data class DataContainer<T>(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<T>
)