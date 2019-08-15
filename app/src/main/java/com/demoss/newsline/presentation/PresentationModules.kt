package com.demoss.newsline.presentation

import com.demoss.newsline.presentation.fragments.fragmentsModule
import com.demoss.newsline.presentation.root.mainModule
import org.koin.dsl.module.Module

val presentationModules = listOf<Module>(fragmentsModule, mainModule)