package com.makinul.mvi.architecture.di


import com.makinul.mvi.architecture.data.repository.MovieRepository
import com.makinul.mvi.architecture.data.repository.MovieRepositoryImpl
import com.makinul.mvi.architecture.ui.MainViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::MovieRepositoryImpl) { bind<MovieRepository>() }
    viewModelOf(::MainViewModel)
}