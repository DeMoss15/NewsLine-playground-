package com.demoss.newsline.data.remote.repository

import org.koin.dsl.module.module

val remoteRepositoryModule = module {
    single { TopHeadlinesRemoteDataSource(get()) as TopHeadlinesRemoteRepository }
}