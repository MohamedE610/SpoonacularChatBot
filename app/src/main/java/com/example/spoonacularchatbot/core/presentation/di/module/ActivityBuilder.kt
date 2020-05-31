package com.example.spoonacularchatbot.core.presentation.di.module


import com.example.spoonacularchatbot.features.chatbot.presentation.di.ChatBotModule
import com.example.spoonacularchatbot.features.chatbot.presentation.view.activity.ChatBotActivity
import com.example.spoonacularchatbot.features.splash.presentation.di.SplashModule
import com.example.spoonacularchatbot.features.splash.presentation.view.activity.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [ChatBotModule::class])
    abstract fun bindChatBotActivity(): ChatBotActivity

}