package com.demoss.newsline.presentation.fragments

import org.koin.dsl.module.module

val fragmentsModule = module {
    factory { NewsModelFactory(get()) }
}