package com.demoss.translatorflashcards.data

import com.demoss.translatorflashcards.data.local.localDataModules
import com.demoss.translatorflashcards.data.remote.remoteDataModules
import com.demoss.translatorflashcards.data.repository.repositoryDataModule

val dataModules = localDataModules +
        remoteDataModules +
        repositoryDataModule