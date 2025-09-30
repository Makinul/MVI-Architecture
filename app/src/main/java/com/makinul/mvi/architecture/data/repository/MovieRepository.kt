package com.makinul.mvi.architecture.data.repository

import com.makinul.mvi.architecture.data.domain.Movie
import kotlinx.coroutines.delay
import javax.inject.Inject

class MovieRepository @Inject constructor() {

    suspend fun getMovies(): List<Movie> {
        // Simulate fetching data from a remote server or database
        delay(2000)
        return listOf(
            Movie(1, "Alita Battle Angel", "2019"),
            Movie(2, "Mortal Engines", "2018"),
            Movie(3, "Avatar The Way of Water", "2022"),
            Movie(4, "Lost in Space", "2018")
        )
    }
}