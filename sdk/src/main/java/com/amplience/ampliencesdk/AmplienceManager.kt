package com.amplience.ampliencesdk

import android.content.Context
import android.util.Log
import com.amplience.ampliencesdk.api.Api
import com.amplience.ampliencesdk.api.RetrofitClient
import com.amplience.ampliencesdk.api.models.ContentResponse
import com.amplience.ampliencesdk.api.models.FilterRequest

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

    suspend fun getViewById(id: String): Result<ContentResponse> {
        val res = try {
            api.getContentById(id)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }

        return if (res.isSuccessful) {
            Result.success(res.body()!!)
        } else {
            Result.failure(Exception(res.errorBody()?.string()))
        }
    }

    suspend fun getFiltered(filterRequest: FilterRequest): Result<List<Map<String, Any>>?> {
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
