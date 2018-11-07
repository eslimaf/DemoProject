package com.eslimaf.coroutines.data.marvel

import com.google.firebase.perf.FirebasePerformance
import okhttp3.Interceptor
import okhttp3.Response

class FirebasePerformanceInterceptor(private val performanceInstance: FirebasePerformance) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // Get request values
        val url = request.url().url()
        val requestPayloadSize = request.body()?.contentLength() ?: 0L
        val httpMethod = request.method()

        //Initialize http trace
        val trace = performanceInstance.newHttpMetric(url, httpMethod)
        trace.setRequestPayloadSize(requestPayloadSize)
        trace.start()

        //Proceed
        val response = chain.proceed(chain.request())

        //Get response values
        val responseCode = response.code()
        val responsePayloadSize = response.body()?.contentLength() ?: 0L

        //Add response values to trace and close it
        trace.setHttpResponseCode(responseCode)
        trace.setResponsePayloadSize(responsePayloadSize)
        trace.stop()

        return response
    }
}