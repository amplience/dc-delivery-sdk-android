package com.amplience.ampliencesdk.api

import com.amplience.ampliencesdk.api.models.ContentRequest
import com.amplience.ampliencesdk.api.models.ContentResponse
import com.amplience.ampliencesdk.api.models.FilterRequest
import com.amplience.ampliencesdk.api.models.PagedResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface Api {

    @GET("content/id/{id}?depth=all&format=inlined")
    suspend fun getContentById(@Path("id") id: String): Response<ContentResponse>

    @GET("content/key/{key}?depth=all&format=inlined")
    suspend fun getContentByKey(@Path("key") key: String): Response<ContentResponse>

    @GET("content/fetch")
    suspend fun getMultipleContent(@Body contentRequest: ContentRequest): Response<ContentResponse>

    @POST("content/filter")
    suspend fun filterContent(@Body filterRequest: FilterRequest): Response<PagedResponse>
}
