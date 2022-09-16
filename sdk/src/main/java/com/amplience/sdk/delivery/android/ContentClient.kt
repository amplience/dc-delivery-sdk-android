package com.amplience.sdk.delivery.android

import android.content.Context
import android.util.Log
import com.amplience.sdk.delivery.android.api.Api
import com.amplience.sdk.delivery.android.api.ContentCallback
import com.amplience.sdk.delivery.android.api.RetrofitClient
import com.amplience.sdk.delivery.android.api.models.FilterBy
import com.amplience.sdk.delivery.android.api.models.FilterContentRequest
import com.amplience.sdk.delivery.android.api.models.FilterContentResponse
import com.amplience.sdk.delivery.android.api.models.ListContentRequest
import com.amplience.sdk.delivery.android.api.models.ListContentResponse
import com.amplience.sdk.delivery.android.api.models.Request
import com.amplience.sdk.delivery.android.api.models.SortBy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentClient private constructor(
    context: Context,
    hub: String,
    private val configuration: Configuration = Configuration()
) {

    data class Configuration @JvmOverloads constructor(
        val freshApiKey: String? = null,
        val stagingEnvironmentUrl: String? = null
    )

    companion object {
        private var sdkManager: ContentClient? = null

        /**
         * [getInstance]
         * Get the current instance of the [ContentClient].
         * Throws a [NotInitialisedException] if called before [initialise]
         */
        @JvmStatic
        fun getInstance(): ContentClient = sdkManager ?: throw NotInitialisedException()

        /**
         * [newInstance]
         * Get a new instance of a [ContentClient].
         * This instance is NOT retained and accessible with [getInstance] - you must keep your own reference
         *
         * @param context
         * @param hub - https://{hub}.cdn.content.amplience.net/
         * @param configuration (optional)
         *
         * @return [ContentClient]
         */
        @JvmStatic
        @JvmOverloads
        fun newInstance(
            context: Context,
            hub: String,
            configuration: Configuration = Configuration()
        ): ContentClient =
            ContentClient(context, hub, configuration)

        /**
         * [initialise]
         * @param context
         * @param hub - https://{hub}.cdn.content.amplience.net/
         * @param configuration (optional)
         *
         * Creates an instance of the [ContentClient] which
         * can be subsequently called with [getInstance]
         */
        @JvmStatic
        @JvmOverloads
        fun initialise(
            context: Context,
            hub: String,
            configuration: Configuration = Configuration()
        ) {
            sdkManager = ContentClient(context, hub, configuration)
        }
    }

    private val cacheBaseUrl =
        if (configuration.stagingEnvironmentUrl != null) "https://${configuration.stagingEnvironmentUrl}/" else "https://$hub.cdn.content.amplience.net/"

    private val freshBaseUrl =
        if (configuration.stagingEnvironmentUrl != null) "https://${configuration.stagingEnvironmentUrl}/" else "https://$hub.fresh.content.amplience.net/"

    private val cacheApi = RetrofitClient
        .getClient(context, cacheBaseUrl)
        .create(Api::class.java)

    private val freshApi = RetrofitClient
        .getClient(context, freshBaseUrl, configuration.freshApiKey)
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
            if (isFresh && configuration.freshApiKey == null) {
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
     * [getContentById]
     * @param id - the id of the object you want to retrieve
     * @param callback - get the result or error
     */
    fun getContentById(id: String, callback: ContentCallback<ListContentResponse?>) {
        val call = api.getContentByIdAsync(id)
        call.enqueue(object : Callback<ListContentResponse> {
            override fun onResponse(
                call: Call<ListContentResponse>,
                response: Response<ListContentResponse>
            ) {
                callback.onSuccess(response.body())
            }

            override fun onFailure(call: Call<ListContentResponse>, t: Throwable) {
                if (t is Exception) {
                    callback.onError(t)
                } else {
                    callback.onError(Exception(t.localizedMessage))
                }
            }
        })
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
     * [getContentByKey]
     * @param key - the key of the object you want to retrieve
     * @param callback - get the result or error
     */
    fun getContentByKey(key: String, callback: ContentCallback<ListContentResponse?>) {
        val call = api.getContentByKeyAsync(key)
        call.enqueue(object : Callback<ListContentResponse> {
            override fun onResponse(
                call: Call<ListContentResponse?>,
                response: Response<ListContentResponse>
            ) {
                callback.onSuccess(response.body())
            }

            override fun onFailure(call: Call<ListContentResponse?>, t: Throwable) {
                if (t is Exception) {
                    callback.onError(t)
                } else {
                    callback.onError(Exception(t.localizedMessage))
                }
            }
        })
    }

    /**
     * [filterContent]
     * @param filters - any number of [FilterBy] key value pairs
     * @param sortBy (optional) - a key [SortBy.key] and optional order
     * @param page (optional) - pagination
     * @param locale (optional) - to override default locale
     *
     * @return [Result][ContentResponse] - returns either a success or failure.
     * Can get successful result with result.getOrNull()
     * Can get error response with result.getExceptionOrNull()
     */
    suspend fun filterContent(
        vararg filters: FilterBy,
        sortBy: SortBy? = null,
        page: FilterContentRequest.Page? = null,
        locale: String? = null
    ): Result<FilterContentResponse?> {
        val filterRequest = FilterContentRequest(
            filterBy = filters.toList(),
            sortBy = sortBy,
            page = page,
            parameters = FilterContentRequest.Parameters(locale = locale)
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
     * [filterContent]
     * @param filters - any number of [FilterBy] key value pairs
     * @param callback - get the result or error
     * @param sortBy (optional) - a key [SortBy.key] and optional order
     * @param page (optional) - pagination
     * @param locale (optional) - to override default locale
     */
    @JvmOverloads
    fun filterContent(
        vararg filters: FilterBy,
        sortBy: SortBy? = null,
        page: FilterContentRequest.Page? = null,
        locale: String? = null,
        callback: ContentCallback<FilterContentResponse?>
    ) {
        val filterRequest = FilterContentRequest(
            filterBy = filters.toList(),
            sortBy = sortBy,
            page = page,
            parameters = FilterContentRequest.Parameters(locale = locale)
        )
        val call = api.filterContentAsync(filterRequest)
        call.enqueue(object : Callback<FilterContentResponse> {
            override fun onResponse(
                call: Call<FilterContentResponse>,
                response: Response<FilterContentResponse>
            ) {
                callback.onSuccess(response.body())
            }

            override fun onFailure(call: Call<FilterContentResponse>, t: Throwable) {
                if (t is Exception) {
                    callback.onError(t)
                } else {
                    callback.onError(Exception(t.localizedMessage))
                }
            }
        })
    }

    /**
     * [listContent]
     * @param requests - ids or keys of content to get
     * @param locale (optional) - to override default locale
     *
     * @return [Result]List<[ContentResponse]>- returns either a success or failure.
     * Can get successful result with result.getOrNull()
     * Can get error response with result.getExceptionOrNull()
     */
    suspend fun listContent(
        vararg requests: Request,
        locale: String? = null
    ): Result<List<ListContentResponse>?> {
        val res = try {
            api.getMultipleContent(
                ListContentRequest(
                    requests.toList(),
                    parameters = FilterContentRequest.Parameters(locale = locale)
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
     * [listContent]
     * @param requests - ids or keys of content to get
     * @param callback - get the result or error
     * @param locale (optional) - to override default locale
     */
    fun listContent(
        vararg requests: Request,
        locale: String? = null,
        callback: ContentCallback<List<ListContentResponse>?>
    ) {
        val call = api.getMultipleContentAsync(
            ListContentRequest(
                requests.toList(),
                parameters = FilterContentRequest.Parameters(locale = locale)
            )
        )
        call.enqueue(object : Callback<List<ListContentResponse>> {
            override fun onResponse(
                call: Call<List<ListContentResponse>>,
                response: Response<List<ListContentResponse>>
            ) {
                callback.onSuccess(response.body())
            }

            override fun onFailure(call: Call<List<ListContentResponse>>, t: Throwable) {
                if (t is Exception) {
                    callback.onError(t)
                } else {
                    callback.onError(Exception(t.localizedMessage))
                }
            }
        })
    }

    /**
     * [listContentById]
     * @param ids - list of ids of content to get
     * @param locale (optional) - to override default locale
     *
     * @return [Result]List<[ContentResponse]>- returns either a success or failure.
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
                    parameters = FilterContentRequest.Parameters(locale = locale)
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
     * [listContentById]
     * @param ids - list of ids of content to get
     * @param callback - get the result or error
     * @param locale (optional) - to override default locale
     */
    fun listContentById(
        vararg ids: String,
        locale: String? = null,
        callback: ContentCallback<List<ListContentResponse>?>
    ) {
        val call = api.getMultipleContentAsync(
            ListContentRequest(
                ids.map { id -> Request(id = id) },
                parameters = FilterContentRequest.Parameters(locale = locale)
            )
        )

        call.enqueue(object : Callback<List<ListContentResponse>> {
            override fun onResponse(
                call: Call<List<ListContentResponse>>,
                response: Response<List<ListContentResponse>>
            ) {
                callback.onSuccess(response.body())
            }

            override fun onFailure(call: Call<List<ListContentResponse>>, t: Throwable) {
                if (t is Exception) {
                    callback.onError(t)
                } else {
                    callback.onError(Exception(t.localizedMessage))
                }
            }
        })
    }

    /**
     * [listContentByKey]
     * @param keys - list of keys of content to get
     * @param locale (optional) - to override default locale
     *
     * @return [Result]List<[ContentResponse]>- returns either a success or failure.
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
                    parameters = FilterContentRequest.Parameters(locale = locale)
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
     * @param callback - get the result or error
     * @param locale (optional) - to override default locale
     */
    fun listContentByKey(
        vararg keys: String,
        locale: String? = null,
        callback: ContentCallback<List<ListContentResponse>?>
    ) {
        val call = api.getMultipleContentAsync(
            ListContentRequest(
                keys.map { key -> Request(key = key) },
                parameters = FilterContentRequest.Parameters(locale = locale)
            )
        )
        call.enqueue(object : Callback<List<ListContentResponse>> {
            override fun onResponse(
                call: Call<List<ListContentResponse>>,
                response: Response<List<ListContentResponse>>
            ) {
                callback.onSuccess(response.body())
            }

            override fun onFailure(call: Call<List<ListContentResponse>>, t: Throwable) {
                if (t is Exception) {
                    callback.onError(t)
                } else {
                    callback.onError(Exception(t.localizedMessage))
                }
            }
        })
    }
}
