package com.demoss.newsline.presentation.fragments

sealed class WordsListCommand
object NEXT_STATE: WordsListCommand()

sealed class WordsListState
data class EmptyErrorState(val message: String): WordsListState()
data class ErrorState(val message: String): WordsListState()
object EmptyState : WordsListState()
data class DataState<T>(val data: List<T>): WordsListState()
object EmptyProgressState: WordsListState()
object DataProgressState: WordsListState()