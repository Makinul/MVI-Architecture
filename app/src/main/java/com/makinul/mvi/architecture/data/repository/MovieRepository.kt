package com.makinul.mvi.architecture.data.repository

import com.makinul.mvi.architecture.data.domain.Movie
import kotlinx.coroutines.delay

interface MovieRepository {
    suspend fun getMovies(): List<Movie>
}

class MovieRepositoryImpl : MovieRepository {

    override suspend fun getMovies(): List<Movie> {
        delay(2000)
        return listOf(
            Movie(1, "Alita Battle Angel", "2019"),
            Movie(2, "Mortal Engines", "2018"),
            Movie(3, "Avatar The Way of Water", "2022"),
            Movie(4, "Lost in Space", "2018")
        )
    }
}