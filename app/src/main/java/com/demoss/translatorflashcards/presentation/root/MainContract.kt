package com.demoss.translatorflashcards.presentation.root

sealed class MainState
object InitialState : MainState()

sealed class MainCommand