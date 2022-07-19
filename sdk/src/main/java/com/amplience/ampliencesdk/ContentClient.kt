package com.amplience.ampliencesdk

import android.content.Context
import android.util.Log
import com.amplience.ampliencesdk.api.Api
import com.amplience.ampliencesdk.api.RetrofitClient
import com.amplience.ampliencesdk.api.models.*
import com.amplience.ampliencesdk.api.models.images.ImageUrlBuilder
import com.amplience.ampliencesdk.media.AmplienceImage
import com.amplience.ampliencesdk.media.AmplienceVideo

class ContentClient private constructor(
    context: Context,
    hub: String,
    private var freshApiKey: String? = null
) {

    companion object {
        private var sdkManager: ContentClient? = null

        /**
         * [getInstance]
         * Get the current instance of the [ContentClient].
         * Throws a [NotInitialisedException] if called before [initialise]
         */
        fun getInstance(): ContentClient = sdkManager ?: throw NotInitialisedException()

        /**
         * [newInstance]
         * Get a new instance of a [ContentClient].
         * This instance is NOT retained and accessible with [getInstance] - you must keep your own reference
         *
         * @param context
         * @param hub - https://{hub}.cdn.content.amplience.net/
         * @param freshApiKey
         *
         * @return [ContentClient]
         */
        fun newInstance(context: Context, hub: String, freshApiKey: String? = null): ContentClient =
            ContentClient(context, hub, freshApiKey)

        /**
         * [initialise]
         * @param context
         * @param hub - https://{hub}.cdn.content.amplience.net/
         * @param freshApiKey
         *
         * Creates an instance of the [ContentClient] which
         * can be subsequently called with [getInstance]
         */
        fun initialise(context: Context, hub: String, freshApiKey: String? = null) {
            sdkManager = ContentClient(context, hub, freshApiKey)
        }
    }

    private val cacheApi = RetrofitClient
        .getClient(context, "https://$hub.cdn.content.amplience.net/")
        .create(Api::class.java)

    private val freshApi = RetrofitClient
        .getClient(context, "https://$hub.fresh.content.amplience.net/", freshApiKey)
        .create(Api::class.java)

    private val api
        get() = if (isFresh) freshApi else cacheApi

    /**
     * [isFresh] - switch between fresh or cached environments
     * See https://amplience.com/docs/development/freshapi/fresh-api.html for details
     *
     * @throws RuntimeException if you have not provided a freshApiKey in the [ContentClient.initialise] method
     */
    var isFresh: Boolean = false
        set(isFresh) {
            field = isFresh
            if (isFresh && freshApiKey == null) {
                throw RuntimeException("Cannot switch to fresh environment without setting a fresh api key")
            }
        }

    /**
     * [getContentById]
     * @param id - the id of the object you want to retrieve
     *
     * @return [Result][ListContentResponse] - returns either a success or failure.
     * Can get successful result with result.getOrNull()
     * Can get error response with result.getExceptionOrNull()
     */
    suspend fun getContentById(id: String): Result<ListContentResponse?> {
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
     * @return [Result][ListContentResponse] - returns either a success or failure.
     * Can get successful result with result.getOrNull()
     * Can get error response with result.getExceptionOrNull()
     */
    suspend fun getContentByKey(key: String): Result<ListContentResponse?> {
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
     * [filterContent]
     * @param filters - any number of [FilterBy] key value pairs
     * @param sortBy (optional) - a key [SortBy.key] and optional order
     * @param page (optional) - pagination
     * @param locale (optional) - to override default locale
     *
     * @return [Result][FilterContentResponse] - returns either a success or failure.
     * Can get successful result with result.getOrNull()
     * Can get error response with result.getExceptionOrNull()
     */
    suspend fun filterContent(
        vararg filters: FilterBy,
        sortBy: SortBy? = null,
        page: Page? = null,
        locale: String? = null
    ): Result<FilterContentResponse?> {
        val filterRequest = FilterContentRequest(
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
            Result.success(res.body())
        } else {
            Result.failure(Exception(res.errorBody()?.string()))
        }
    }

    /**
     * [listContent]
     * @param requests - ids or keys of content to get
     * @param locale (optional) - to override default locale
     *
     * @return [Result]List<[ListContentResponse]>- returns either a success or failure.
     * Can get successful result with result.getOrNull()
     * Can get error response with result.getExceptionOrNull()
     */
    suspend fun listContent(
        vararg requests: Request,
        locale: String? = null
    ): Result<List<ListContentResponse>?> {
        val res = try {
            api.getMultipleContent(
                ListContentRequest(requests.toList(), parameters = Parameters(locale = locale))
            )
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
     * [listContentById]
     * @param ids - list of ids of content to get
     * @param locale (optional) - to override default locale
     *
     * @return [Result]List<[ListContentResponse]>- returns either a success or failure.
     * Can get successful result with result.getOrNull()
     * Can get error response with result.getExceptionOrNull()
     */
    suspend fun listContentById(
        vararg ids: String,
        locale: String? = null
    ): Result<List<ListContentResponse>?> {
        val res = try {
            api.getMultipleContent(
                ListContentRequest(
                    ids.map { id -> Request(id = id) },
                    parameters = Parameters(locale = locale)
                )
            )
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
     * [listContentByKey]
     * @param keys - list of keys of content to get
     * @param locale (optional) - to override default locale
     *
     * @return [Result]List<[ListContentResponse]>- returns either a success or failure.
     * Can get successful result with result.getOrNull()
     * Can get error response with result.getExceptionOrNull()
     */
    suspend fun listContentByKey(
        vararg keys: String,
        locale: String? = null
    ): Result<List<ListContentResponse>?> {
        val res = try {
            api.getMultipleContent(
                ListContentRequest(
                    keys.map { key -> Request(key = key) },
                    parameters = Parameters(locale = locale)
                )
            )
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
