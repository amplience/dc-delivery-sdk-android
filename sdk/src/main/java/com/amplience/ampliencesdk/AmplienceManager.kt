package com.amplience.ampliencesdk

import android.content.Context
import android.util.Log
import com.amplience.ampliencesdk.api.Api
import com.amplience.ampliencesdk.api.RetrofitClient
import com.amplience.ampliencesdk.api.models.*
import com.amplience.ampliencesdk.api.models.images.ImageUrlBuilder
import com.amplience.ampliencesdk.media.AmplienceImage
import com.amplience.ampliencesdk.media.AmplienceVideo

class AmplienceManager private constructor(context: Context, hub: String) {

    companion object {
        private var sdkManager: AmplienceManager? = null

        /**
         * [getInstance]
         * Get the current instance of the [AmplienceManager].
         * Throws a [NotInitialisedException] if called before [initialise]
         */
        fun getInstance(): AmplienceManager = sdkManager ?: throw NotInitialisedException()

        /**
         * [initialise]
         * @param context
         * @param hub - https://{hub}.cdn.content.amplience.net/
         *
         * Creates an instance of the [AmplienceManager] which
         * can be subsequently called with [getInstance]
         */
        fun initialise(context: Context, hub: String) {
            sdkManager = AmplienceManager(context, hub)
        }
    }

    private val api = RetrofitClient
        .getClient(context, "https://$hub.cdn.content.amplience.net/")
        .create(Api::class.java)

    /**
     * [getContentById]
     * @param id - the id of the object you want to retrieve
     *
     * @return [Result][ContentResponse] - returns either a success or failure.
     * Can get successful result with result.getOrNull()
     * Can get error response with result.getExceptionOrNull()
     */
    suspend fun getContentById(id: String): Result<ContentResponse?> {
        val res = try {
            api.getContentById(id)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }

        return if (res.isSuccessful) {
            Result.success(res.body())
        } else {
            Result.failure(Exception(res.errorBody()?.string()))
        }
    }

    /**
     * [getContentByKey]
     * @param key - the key of the object you want to retrieve
     *
     * @return [Result][ContentResponse] - returns either a success or failure.
     * Can get successful result with result.getOrNull()
     * Can get error response with result.getExceptionOrNull()
     */
    suspend fun getContentByKey(key: String): Result<ContentResponse?> {
        val res = try {
            api.getContentByKey(key)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }

        return if (res.isSuccessful) {
            Result.success(res.body())
        } else {
            Result.failure(Exception(res.errorBody()?.string()))
        }
    }

    /**
     * [getContentByFilters]
     * @param filters - any number of [FilterBy] key value pairs
     * @param sortBy (optional) - a key [SortBy.key] and optional order
     * @param page (optional) - pagination
     * @param locale (optional) - to override default locale
     *
     * @return [Result][ContentResponse] - returns either a success or failure.
     * Can get successful result with result.getOrNull()
     * Can get error response with result.getExceptionOrNull()
     */
    suspend fun getContentByFilters(
        vararg filters: FilterBy,
        sortBy: SortBy? = null,
        page: Page? = null,
        locale: String? = null
    ): Result<List<ContentResponse>?> {
        val filterRequest = FilterRequest(
            filterBy = filters.toList(),
            sortBy = sortBy,
            page = page,
            parameters = Parameters(locale = locale)
        )
        val res = try {
            api.filterContent(filterRequest)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }

        Log.d("getFiltered", res.body().toString())
        return if (res.isSuccessful) {
            Result.success(res.body()?.responses)
        } else {
            Result.failure(Exception(res.errorBody()?.string()))
        }
    }

    /**
     * [getImageUrl] returns a url that can be used with any image loading libraries
     *
     * @param image - your implementation of an [AmplienceImage]
     * @param builder (optional) - manipulate the image. See [ImageUrlBuilder] for more info
     */
    fun getImageUrl(
        image: AmplienceImage,
        builder: ImageUrlBuilder.() -> Unit = {}
    ): String {
        var string = "https://${image.defaultHost}/i/${image.endpoint}/${image.name}"
        string += ImageUrlBuilder().apply(builder).build()
        return string
    }

    /**
     * [getVideoUrl] returns a url that can be used with any video loading libraries
     *
     * @param video - your implementation of an [AmplienceVideo]
     */
    fun getVideoUrl(
        video: AmplienceVideo
    ): String = "https://${video.defaultHost}/v/${video.endpoint}/${video.name}/mp4_720p"

    /**
     * [getVideoThumbnailUrl] returns a url that can be used with any image loading libraries
     *
     * @param video - your implementation of an [AmplienceVideo]
     * @param builder (optional) - manipulate the thumbnail image. See [ImageUrlBuilder] for more info
     * @param thumbName (optional) - the specific thumb frame
     *     e.g. https://cdn.media.amplience.net/v/ampproduct/ski-collection/thumbs/frame_0020.png
     */
    fun getVideoThumbnailUrl(
        video: AmplienceVideo,
        builder: ImageUrlBuilder.() -> Unit = {},
        thumbName: String? = null
    ): String {
        var string = "https://${video.defaultHost}/v/${video.endpoint}/${video.name}"
        if (thumbName != null) string += "/thumbs/$thumbName"
        string += ImageUrlBuilder().apply(builder).build()
        return string
    }
}
