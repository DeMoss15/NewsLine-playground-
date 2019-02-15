package com.demoss.translatorflashcards.util.pagination

sealed class ReactivePaginatorViewState
object EMPTY : ReactivePaginatorViewState()
object EMPTY_PROGRESS : ReactivePaginatorViewState()
object EMPTY_ERROR : ReactivePaginatorViewState()
object EMPTY_DATA : ReactivePaginatorViewState()
object PAGE_PROGRESS : ReactivePaginatorViewState()
object REFRESH : ReactivePaginatorViewState()
object RELEASED : ReactivePaginatorViewState()
data class DATA<T>(val ata: List<T>) : ReactivePaginatorViewState()
data class LAST_PAGE<T>(val ata: List<T>) : ReactivePaginatorViewState()