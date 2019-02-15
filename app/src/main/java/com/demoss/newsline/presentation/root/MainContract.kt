package com.demoss.newsline.presentation.root

sealed class MainState
object InitialState : MainState()

sealed class MainCommand