package com.demoss.translatorflashcards.data.remote

import com.demoss.translatorflashcards.data.remote.api.apiModule
import com.demoss.translatorflashcards.data.remote.repository.remoteRepositoryModule

val remoteDataModules = listOf(
    apiModule,
    remoteRepositoryModule,
    networkModule
)