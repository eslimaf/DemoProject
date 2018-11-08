package com.eslimaf.coroutines.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eslimaf.coroutines.data.marvel.MarvelService
import com.eslimaf.coroutines.data.marvel.api.model.entity.Comic
import com.eslimaf.coroutines.data.marvel.api.model.entity.Series
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext = job + Dispatchers.Main

    override fun onCleared() {
        // Cancel any pending coroutines
        job.cancel()
        super.onCleared()
    }

    fun loadCharacterComics(
        id: Int,
        loader: ((Boolean) -> Unit) = {},
        error: (Exception) -> Unit = {}
    ): LiveData<List<Comic>> {
        loader(true)
        val result = MutableLiveData<List<Comic>>()
        launch {
            try {
                val response = MarvelService.loadCharacterComics(id)
                result.value = response.dataContainer.results
            } catch (e: Exception) {
                Log.e("REQUEST_ERROR", e.message)
                error(e)
            } finally {
                loader(false)
            }
        }

        return result
    }

    fun loadCharacterStories(
        id: Int,
        loader: (Boolean) -> Unit = {},
        error: (Exception) -> Unit = {}
    ): LiveData<List<Series>> {
        loader(true)
        val result = MutableLiveData<List<Series>>()
        launch {
            try {
                val response = MarvelService.loadCharacterSeries(id)
                result.value = response.dataContainer.results
            } catch (e: Exception) {
                Log.e("REQUEST_ERROR", e.message)
                error(e)
            } finally {
                loader(false)
            }
        }

        return result
    }
}