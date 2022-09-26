package com.amplience.sdk.delivery.android.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal object RetrofitClient {

    // Store retrofit instances by their base url, separated by cache or fresh
    private var cacheClients: HashMap<String, Retrofit?> = hashMapOf()
    private var freshClients: HashMap<String, Retrofit?> = hashMapOf()

    fun getClient(baseUrl: String, freshApiKey: String? = null): Retrofit {
        // Find existing client in relevant map depending on whether fresh api key is present
        val existing = if (freshApiKey == null) {
            cacheClients[baseUrl]
        } else {
            freshClients[baseUrl]
        }
        if (existing != null) {
            return existing
        } else {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)

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

            val new = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client.build())
                .build()

            // Store new client in relevant map
            if (freshApiKey == null) {
                cacheClients[baseUrl] = new
            } else {
                freshClients[baseUrl] = new
            }

            return new
        }
    }
}
