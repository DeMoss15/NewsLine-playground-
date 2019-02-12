package com.demoss.translatorflashcards

import com.demoss.translatorflashcards.data.dataModules
import com.demoss.translatorflashcards.domain.domainModules
import com.demoss.translatorflashcards.presentation.presentationModules
import org.koin.dsl.module.Module

val applicationModule: List<Module> = presentationModules +
        domainModules +
        dataModules