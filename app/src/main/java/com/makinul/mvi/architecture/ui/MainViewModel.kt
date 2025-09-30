package com.makinul.mvi.architecture.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makinul.mvi.architecture.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(MovieViewState())
    val state: StateFlow<MovieViewState> = _state

    fun handleIntent(intent: MovieIntent) {
        viewModelScope.launch {
            when (intent) {
                is MovieIntent.LoadMovies -> fetchMovies()
                // ... Other Intents goes here...
            }
        }
    }

    private suspend fun fetchMovies() {
        _state.value = _state.value.copy(loading = true, error = null)
        try {
            val movies = repository.getMovies()
            _state.value = MovieViewState(loading = false, movies = movies)
        } catch (e: Exception) {
            _state.value =
                MovieViewState(loading = false, error = e.message ?: "Error fetching movies")
        }
    }
}