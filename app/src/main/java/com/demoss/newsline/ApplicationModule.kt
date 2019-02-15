package com.demoss.newsline

import com.demoss.newsline.data.dataModules
import com.demoss.newsline.domain.domainModules
import com.demoss.newsline.presentation.presentationModules
import org.koin.dsl.module.Module

val applicationModule: List<Module> = presentationModules +
        domainModules +
        dataModules