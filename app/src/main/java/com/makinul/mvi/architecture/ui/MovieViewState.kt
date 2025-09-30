package com.makinul.mvi.architecture.ui

import com.makinul.mvi.architecture.data.domain.Movie

data class MovieViewState(
    val loading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String? = null,
)
