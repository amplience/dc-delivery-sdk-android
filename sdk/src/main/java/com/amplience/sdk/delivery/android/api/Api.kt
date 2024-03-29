package com.amplience.sdk.delivery.android.api

import com.amplience.sdk.delivery.android.api.models.FilterContentRequest
import com.amplience.sdk.delivery.android.api.models.FilterContentResponse
import com.amplience.sdk.delivery.android.api.models.ListContentRequest
import com.amplience.sdk.delivery.android.api.models.ListContentResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @GET("content/id/{id}?depth=all&format=inlined")
    suspend fun getContentById(@Path("id") id: String): Response<ListContentResponse>

    @GET("content/id/{id}?depth=all&format=inlined")
    fun getContentByIdAsync(@Path("id") id: String): Call<ListContentResponse>

    @GET("content/key/{key}?depth=all&format=inlined")
    suspend fun getContentByKey(@Path("key") key: String): Response<ListContentResponse>

    @GET("content/key/{key}?depth=all&format=inlined")
    fun getContentByKeyAsync(@Path("key") key: String): Call<ListContentResponse>

    @GET("content/fetch")
    suspend fun getMultipleContent(@Body contentRequest: ListContentRequest): Response<List<ListContentResponse>>

    @GET("content/fetch")
    fun getMultipleContentAsync(@Body contentRequest: ListContentRequest): Call<List<ListContentResponse>>

    @POST("content/filter")
    suspend fun filterContent(@Body filterRequest: FilterContentRequest): Response<FilterContentResponse>

    @POST("content/filter")
    fun filterContentAsync(@Body filterRequest: FilterContentRequest): Call<FilterContentResponse>
}
