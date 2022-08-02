package com.amplience.ampliencesdk.api

import com.amplience.ampliencesdk.api.models.FilterContentRequest
import com.amplience.ampliencesdk.api.models.FilterContentResponse
import com.amplience.ampliencesdk.api.models.ListContentRequest
import com.amplience.ampliencesdk.api.models.ListContentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface Api {

    @GET("content/id/{id}?depth=all&format=inlined")
    suspend fun getContentById(@Path("id") id: String): Response<ListContentResponse>

    @GET("content/key/{key}?depth=all&format=inlined")
    suspend fun getContentByKey(@Path("key") key: String): Response<ListContentResponse>

    @GET("content/fetch")
    suspend fun getMultipleContent(@Body contentRequest: ListContentRequest): Response<List<ListContentResponse>>

    @POST("content/filter")
    suspend fun filterContent(@Body filterRequest: FilterContentRequest): Response<FilterContentResponse>
}
