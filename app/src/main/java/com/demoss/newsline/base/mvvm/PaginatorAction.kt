package com.demoss.newsline.base.mvvm

sealed class PaginatorAction

object PAGINATOR_RESTART: PaginatorAction()
object PAGINATOR_REFRESH: PaginatorAction()
object PAGINATOR_LOAD_NEXT_PAGE: PaginatorAction()