package com.demoss.newsline.presentation.root

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val mainModule = module {
    viewModel { MainViewModel() }
}