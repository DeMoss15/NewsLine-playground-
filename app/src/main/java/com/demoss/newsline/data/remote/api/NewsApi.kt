package com.demoss.newsline.data.remote.api

import com.demoss.newsline.data.remote.api.model.ResponseObject
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsApi {

    @GET("/v2/everything")
    fun getNews(@QueryMap options: Map<String, String>): Observable<Response<ResponseObject>>
}