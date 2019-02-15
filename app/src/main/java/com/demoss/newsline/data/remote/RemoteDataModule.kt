package com.demoss.newsline.data.remote

import com.demoss.newsline.data.remote.api.apiModule
import com.demoss.newsline.data.remote.repository.remoteRepositoryModule

val remoteDataModules = listOf(
    apiModule,
    remoteRepositoryModule,
    networkModule
)