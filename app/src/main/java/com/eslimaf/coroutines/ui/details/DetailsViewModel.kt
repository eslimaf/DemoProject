package com.eslimaf.coroutines.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eslimaf.coroutines.data.marvel.MarvelService
import com.eslimaf.coroutines.data.marvel.api.model.entity.Comic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext = job + Dispatchers.Main

    //Syntax sugary and cinnamon
    private var shouldShowLoader: ((Boolean) -> Unit)? = null

    override fun onCleared() {
        // Cancel any pending coroutines
        job.cancel()
        super.onCleared()
    }

    fun showLoader(showLoaderListener: (Boolean) -> Unit) {
        shouldShowLoader = showLoaderListener
    }

    fun loadCharacterComics(id: Int): LiveData<List<Comic>> {
        shouldShowLoader?.invoke(true)
        val result = MutableLiveData<List<Comic>>()
        launch {
            try {
                val response = MarvelService.loadCharacterComics(id)
                result.value = response.dataContainer.results
            } catch (e: Exception) {
                Log.e("REQUEST_ERROR", e.message)
            } finally {
                shouldShowLoader?.invoke(false)
            }
        }

        return result
    }
}