package com.example.spoonacularchatbot.core.presentation.di.module


import com.example.spoonacularchatbot.features.splash.presentation.di.SplashModule
import com.example.spoonacularchatbot.features.splash.presentation.view.activity.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun bindHomeActivity(): SplashActivity

}