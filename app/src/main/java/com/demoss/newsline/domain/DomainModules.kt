package com.demoss.newsline.domain

import com.demoss.newsline.domain.model.modelModule
import com.demoss.newsline.domain.usecase.useCaseModule

val domainModules = listOf(
    useCaseModule,
    modelModule
)