package com.demoss.newsline.util.pagination

sealed class PaginatorViewState<T>
class EMPTY<T> : PaginatorViewState<T>()
class EMPTY_PROGRESS<T> : PaginatorViewState<T>()
class EMPTY_ERROR<T> : PaginatorViewState<T>()
class EMPTY_DATA<T> : PaginatorViewState<T>()
data class PAGE_PROGRESS<T>(val data: List<T>)  : PaginatorViewState<T>()
data class REFRESH<T>(val data: List<T>)  : PaginatorViewState<T>()
data class RELEASED<T>(val data: List<T>)  : PaginatorViewState<T>()
data class DATA<T>(val data: List<T>) : PaginatorViewState<T>()
data class LAST_PAGE<T>(val data: List<T>) : PaginatorViewState<T>()