package com.demoss.newsline.data.repository

import org.koin.dsl.module.module

val repositoryDataModule = module {
    single { TopHeadlinesDataSource(get()) as TopHeadlinesRepository }
}