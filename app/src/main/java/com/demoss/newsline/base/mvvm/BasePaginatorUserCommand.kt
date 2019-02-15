package com.demoss.newsline.base.mvvm

sealed class BasePaginatorUserCommand

object PAGINATOR_RESTART: BasePaginatorUserCommand()
object PAGINATOR_REFRESH: BasePaginatorUserCommand()
object PAGINATOR_LOAD_NEXT_PAGE: BasePaginatorUserCommand()