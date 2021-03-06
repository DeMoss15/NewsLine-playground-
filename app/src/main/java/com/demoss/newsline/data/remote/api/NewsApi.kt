package com.demoss.newsline.data.remote.api

import com.demoss.newsline.Constants
import com.demoss.newsline.data.remote.api.model.ResponseObject
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsApi {

    @GET("/v2/top-headlines")
    fun getTopNewsAsync(@QueryMap options: Map<String, String?>): Deferred<Response<ResponseObject>>
}

fun getBaseRequest(): Map<String, String> = mapOf(
    "language" to "en",
    NewsApiConstants.API_KEY to Constants.API_KEY,
    NewsApiConstants.PAGE_SIZE to Constants.PAGE_SIZE.toString()
)