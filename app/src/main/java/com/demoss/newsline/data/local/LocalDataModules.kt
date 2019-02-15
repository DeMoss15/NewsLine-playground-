package com.demoss.newsline.data.local

import com.demoss.newsline.data.local.repository.localRepositoryModule

val localDataModules = listOf(
    dbModule,
    localRepositoryModule
)