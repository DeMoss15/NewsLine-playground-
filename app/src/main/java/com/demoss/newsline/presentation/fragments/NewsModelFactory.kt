package com.demoss.newsline.presentation.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demoss.newsline.domain.usecase.GetTopHeadlinesUseCase

class NewsModelFactory(private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == NewsViewModel::class.java) NewsViewModel(getTopHeadlinesUseCase) as T
        else super.create(modelClass)
    }
}