package com.demoss.translatorflashcards.domain

import com.demoss.translatorflashcards.domain.model.modelModule
import com.demoss.translatorflashcards.domain.usecase.useCaseModule

val domainModules = listOf(
    useCaseModule,
    modelModule
)