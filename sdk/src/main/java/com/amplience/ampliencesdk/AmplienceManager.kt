package com.amplience.ampliencesdk

import android.content.Context
import android.util.Log
import com.amplience.ampliencesdk.api.Api
import com.amplience.ampliencesdk.api.RetrofitClient
import com.amplience.ampliencesdk.api.models.*
import com.amplience.ampliencesdk.api.models.images.*
import com.amplience.ampliencesdk.media.Image

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
        return getContentByFilterRequest(
            FilterRequest(
                filterBy = filters.toList(),
                sortBy = sortBy,
                page = page,
                parameters = Parameters(locale = locale)
            )
        )
    }

    private suspend fun getContentByFilterRequest(filterRequest: FilterRequest): Result<List<ContentResponse>?> {
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
     * @param width Sets the width of the image. If you only provide the width, the height will be calculated to maintain the aspect ratio.
     * @param height Sets the height of the image. If you only provide the height, the width will be calculated to maintain the aspect ratio.
     * @param quality Sets the compression quality of the image, this is a percentage 0-100. JPEGs are lossy, PNGs are lossless but are compressed with zlib.
     * @param defaultQuality Specifies that the default quality should be used for the following
    formats: webp,jp2,jpeg or png. Can be used with fmt=auto or on its own. See auto format
    for more details. The default settings for each format are:
    jp2 40
    webp 80
    jpeg 75
    png 90
     *
     */

    fun getImageUrl(
        image: Image,
        builder: ImageUrlBuilder.() -> Unit = {}
    ): String {
        var string = "https://${image.defaultHost}/i/${image.endpoint}/${image.name}"
        string += ImageUrlBuilder().apply(builder).build()
        return string
    }

    fun getImageUrl(
        image: Image,
        width: Int? = null,
        height: Int? = null,
        quality: Int? = null,
        defaultQuality: Boolean? = null,
        /*formatQuality: FormatQuality? = null,*/
        scaleMode: ScaleMode? = null,
        scaleFit: ScaleFit? = null,
        resizeAlgorithm: ResizeAlgorithm? = null,
        upscale: Upscale? = null,
        maxWidth: Int? = null,
        maxHeight: Int? = null
    ): String {
        val builder = StringBuilder()
        builder.append("https://${image.defaultHost}/i/${image.endpoint}/${image.name}")

        var firstQuery = true

        fun addQueryMarker() {
            if (firstQuery) {
                builder.append("?")
                firstQuery = false
            } else {
                builder.append("&")
            }
        }

        if (width != null) {
            addQueryMarker()
            builder.append("w=$width")
        }

        if (height != null) {
            addQueryMarker()
            builder.append("h=$height")
        }

        if (quality != null) {
            addQueryMarker()
            builder.append("qlt=$quality")
        }

        if (defaultQuality != null && defaultQuality) {
            addQueryMarker()
            builder.append("qlt=default")
        }

        if (scaleMode != null) {
            addQueryMarker()
            builder.append("sm=$scaleMode")
        }

        if (scaleFit != null) {
            addQueryMarker()
            builder.append("scalefit=$scaleFit")
        }

        if (resizeAlgorithm != null) {
            addQueryMarker()
            builder.append("filter=$resizeAlgorithm")
        }

        if (upscale != null) {
            addQueryMarker()
            builder.append("upscale=$upscale")
        }

        if (maxWidth != null) {
            addQueryMarker()
            builder.append("maxW=$maxWidth")
        }

        if (maxHeight != null) {
            addQueryMarker()
            builder.append("maxH=$maxHeight")
        }

        return builder.toString()
    }

}
