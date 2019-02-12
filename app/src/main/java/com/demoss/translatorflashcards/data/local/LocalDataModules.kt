package com.demoss.translatorflashcards.data.local

import com.demoss.translatorflashcards.data.local.repository.localRepositoryModule

val localDataModules = listOf(
    dbModule,
    localRepositoryModule
)