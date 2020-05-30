package com.example.spoonacularchatbot.features.splash.presentation.di

import com.example.spoonacularchatbot.features.splash.data.repository.QARepositoryImpl
import com.example.spoonacularchatbot.features.splash.domain.repository.QARepository
import dagger.Binds
import dagger.Module

@Module
abstract class SplashModule {
    @Binds
    abstract fun bindQARepository(repository: QARepositoryImpl): QARepository
}