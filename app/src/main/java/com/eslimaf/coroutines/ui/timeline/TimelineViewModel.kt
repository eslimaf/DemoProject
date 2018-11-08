package com.eslimaf.coroutines.ui.timeline

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eslimaf.coroutines.data.marvel.MarvelService
import com.eslimaf.coroutines.data.marvel.api.model.ApiResponse
import com.eslimaf.coroutines.data.marvel.api.model.entity.Character
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TimelineViewModel : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext = job + Main

    private var shouldShowLoader: ((Boolean) -> Unit)? = null
    private var shouldShowError: ((Boolean) -> Unit)? = null

    fun setShowLoaderListener(showLoaderListener: (Boolean) -> Unit) {
        shouldShowLoader = showLoaderListener
    }

    fun setShowErrorListener(showErrorListener: (Boolean) -> Unit) {
        shouldShowError = showErrorListener
    }

    override fun onCleared() {
        // Cancel any pending coroutines
        job.cancel()
        super.onCleared()
    }

    fun load(page: Int): LiveData<List<Character>> {
        val result = MutableLiveData<List<Character>>()
        MarvelService.loadCharacters(page).enqueue(object : Callback<ApiResponse<Character>> {
            override fun onFailure(call: Call<ApiResponse<Character>>, t: Throwable) {
                Log.e("REQUEST_ERROR", call.request().body().toString())
            }

            override fun onResponse(
                call: Call<ApiResponse<Character>>,
                response: Response<ApiResponse<Character>>
            ) {
                response.body()?.let {
                    result.value = it.dataContainer.results
                }
            }
        })
        return result
    }

    fun loadWithClass(page: Int): LiveData<List<Character>> {
        shouldShowLoader?.invoke(true)
        val result = MutableLiveData<List<Character>>()
        launch {
            try {
                val apiResponse = withContext(Dispatchers.IO) { MarvelService.loadChars(page) }
//                val apiResponse =
//                    withTimeout(2000) { withContext(Dispatchers.IO) { MarvelService.loadChars(page) } }
                result.value = apiResponse.dataContainer.results
            } catch (e: Exception) {
                Log.e("REQUEST_ERROR", e.message)
            } finally {
                shouldShowLoader?.invoke(false)
            }
        }
        return result
    }
}
