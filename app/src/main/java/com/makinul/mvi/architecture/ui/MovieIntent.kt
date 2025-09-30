package com.makinul.mvi.architecture.ui

sealed class MovieIntent {
    object LoadMovies : MovieIntent()
}