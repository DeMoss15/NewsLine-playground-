package com.demoss.newsline.data.remote

import com.demoss.newsline.application.Constants
import com.demoss.newsline.application.Constants.CONNECTION_TIMEOUT
import com.demoss.newsline.application.Constants.READ_TIMEOUT
import com.demoss.newsline.application.Constants.WRITE_TIMEOUT
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        // Retrofit
        Retrofit.Builder().apply {
            baseUrl(Constants.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            client(get())
        }.build()
    }
    single {
        // OkHttp
        OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        }.build()
    }
}