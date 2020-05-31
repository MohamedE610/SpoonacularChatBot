package com.example.spoonacularchatbot.features.chatbot.presentation.di

import com.example.spoonacularchatbot.core.data.remote.ServiceGenerator
import com.example.spoonacularchatbot.features.chatbot.data.repository.ChatBotRepositoryImpl
import com.example.spoonacularchatbot.features.chatbot.data.source.remote.ChatBotRemoteDataSource
import com.example.spoonacularchatbot.features.chatbot.data.source.remote.ChatBotRemoteDataSourceImpl
import com.example.spoonacularchatbot.features.chatbot.data.source.remote.SpoonacularApi
import com.example.spoonacularchatbot.features.chatbot.domain.repository.ChatBotRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [ChatBotModule.BindsModule::class])
class ChatBotModule {

    @Provides
    fun providesSpoonacularApi() = ServiceGenerator().createService(SpoonacularApi::class.java)

    @Module
    interface BindsModule {
        @Binds
        fun bindChatBotRepository(repositoryImpl: ChatBotRepositoryImpl): ChatBotRepository

        @Binds
        fun bindChatBotRemoteDataSource(remoteDataSourceImpl: ChatBotRemoteDataSourceImpl): ChatBotRemoteDataSource
    }

}