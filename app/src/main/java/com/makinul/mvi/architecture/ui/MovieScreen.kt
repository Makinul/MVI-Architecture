package com.makinul.mvi.architecture.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.makinul.mvi.architecture.data.domain.Movie

@Composable
fun MovieScreen(mainViewModel: MainViewModel) {
    val state by mainViewModel.state.collectAsState()

    LaunchedEffect(mainViewModel) {
        // Trigger the fetchMovies() when the composable is first launched.
        mainViewModel.handleIntent(MovieIntent.LoadMovies)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            state.loading -> {
                // Display a loading indicator
                CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
            }

            state.error != null -> {
                // Display an error message
                Text(text = "Error: ${state.error}", color = Color.Red)
            }

            else -> {
                // Display the list of movies
                MoviesList(movies = state.movies)
            }
        }
    }

}

@Composable
fun MoviesList(movies: List<Movie>) {
    LazyColumn {
        items(movies) { movie ->
            // Display a movie item
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .shadow(4.dp, RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = "Movie: ${movie.title}",
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = "Date: ${movie.year}",
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}