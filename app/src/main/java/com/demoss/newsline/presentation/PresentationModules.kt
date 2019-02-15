package com.demoss.newsline.presentation

import com.demoss.newsline.presentation.fragments.fragmentsModule
import org.koin.dsl.module.Module

val presentationModules = listOf<Module>(fragmentsModule)