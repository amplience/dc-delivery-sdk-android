package com.amplience.sdk.delivery.android.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal object RetrofitClient {

    // Store retrofit instances by their base url, separated by cache or fresh
    private var cacheClients: HashMap<String, Retrofit> = hashMapOf()
    private var freshClients: HashMap<String, Retrofit> = hashMapOf()

    fun getClient(baseUrl: String, freshApiKey: String? = null): Retrofit {
        val cache = if (freshApiKey == null) cacheClients else freshClients

        val existingClient = cache[baseUrl]

        return if (existingClient != null) {
            existingClient
        } else {
            val client = newClient(baseUrl, freshApiKey)
            cache[baseUrl] = client
            client
        }
    }

    private fun newClient(baseUrl: String, freshApiKey: String? = null): Retrofit {
        val client = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(interceptor)

        if (freshApiKey != null) {
            client.addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.header("X-API-Key", freshApiKey)
                chain.proceed(requestBuilder.build())
            }
        }

        val gson = GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
            .create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client.build())
            .build()
    }
}