package com.demoss.newsline.base.mvvm

sealed class BasePaginatorUserCommand

object RESTART: BasePaginatorUserCommand()
object REFRESH: BasePaginatorUserCommand()
object LOAD_NEXT_PAGE: BasePaginatorUserCommand()