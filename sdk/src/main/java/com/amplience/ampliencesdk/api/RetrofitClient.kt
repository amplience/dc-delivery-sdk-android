package com.amplience.ampliencesdk.api

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal object RetrofitClient {

    private var retrofit: Retrofit? = null

    fun getClient(context: Context, baseUrl: String, freshApiKey: String? = null): Retrofit {
        if (retrofit == null) {
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

            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client.build())
                .build()
        }

        return retrofit!!
    }
}
