package com.eslimaf.coroutines.data.marvel

import com.eslimaf.coroutines.constants.Constants.BASE_URL
import com.eslimaf.coroutines.constants.Constants.PRIV_KEY
import com.eslimaf.coroutines.constants.Constants.PUB_KEY
import com.eslimaf.coroutines.data.marvel.api.model.ApiResponse
import com.eslimaf.coroutines.data.marvel.api.model.entity.Character
import com.eslimaf.coroutines.data.marvel.api.model.entity.Comic
import com.eslimaf.coroutines.extensions.md5
import com.google.firebase.perf.FirebasePerformance
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object MarvelService {
    private val api: MarvelApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(provideGsonConverterFactory())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(provideOkHttpClient())
            .build()
        retrofit.create(MarvelApi::class.java)
    }

    fun loadCharacters(page: Int = 0): Call<ApiResponse<Character>> = api.getCharacters(page * 20)

    suspend fun loadChars(page: Int = 0): ApiResponse<Character> = api.getCharactersEx(page).await()

    suspend fun loadCharacterDetails(id: Int): ApiResponse<Character> =
        api.getCharacterDetails(id).await()

    suspend fun loadCharacterComics(id: Int): ApiResponse<Comic> =
        api.getCharacterComics(id).await()

    //region Providers
    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideLoggingInterceptor())
            .addInterceptor(FirebasePerformanceInterceptor(FirebasePerformance.getInstance()))
            .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()

                val ts =
                    (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apikey", PUB_KEY)
                    .addQueryParameter("ts", ts)
                    .addQueryParameter("hash", "$ts$PRIV_KEY$PUB_KEY".md5())
                    .build()
                val requestBuilder = original.newBuilder().url(url)

                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder().setLenient().create()
        return GsonConverterFactory.create(gson)
    }
    //endregion

}