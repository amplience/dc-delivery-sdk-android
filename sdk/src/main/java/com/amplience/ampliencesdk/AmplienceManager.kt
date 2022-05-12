package com.amplience.ampliencesdk

import android.content.Context
import android.util.Log
import com.amplience.ampliencesdk.api.Api
import com.amplience.ampliencesdk.api.RetrofitClient
import com.amplience.ampliencesdk.api.models.*

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
}
