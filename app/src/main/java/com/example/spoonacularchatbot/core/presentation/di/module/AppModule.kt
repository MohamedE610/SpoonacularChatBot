package com.example.spoonacularchatbot.core.presentation.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.spoonacularchatbot.core.data.local.cache.QuestionsLocalDataSource
import com.example.spoonacularchatbot.core.data.local.cache.QuestionsLocalDataSourceImpl
import com.example.spoonacularchatbot.core.data.local.dao.QuestionDao
import com.example.spoonacularchatbot.core.data.local.db.SpoonacularChatBotDB
import com.example.spoonacularchatbot.core.presentation.common.AppConstants
import com.example.spoonacularchatbot.core.presentation.di.qualifier.DatabaseInfo
import com.example.spoonacularchatbot.core.presentation.di.qualifier.PreferenceInfo
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule.BindsModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return AppConstants.DB_NAME
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return AppConstants.PREF_NAME
    }

    @Provides
    @Singleton
    @PreferenceInfo
    fun providePreferenceObj(
        @PreferenceInfo spName: String,
        application: Application
    ): SharedPreferences {
        return application.getSharedPreferences(spName, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSpoonacularChatBotDB(
        @DatabaseInfo dbName: String,
        context: Context
    ): SpoonacularChatBotDB {
        return Room.databaseBuilder(context, SpoonacularChatBotDB::class.java, dbName)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideQuestionsDao(
        database: SpoonacularChatBotDB
    ): QuestionDao {
        return database.questionDao()
    }

    @Module
    interface BindsModule {
        @Binds
        @Singleton
        fun bindQuestionsLocalDataSource(
            questionsLocalDataSourceImpl: QuestionsLocalDataSourceImpl
        ): QuestionsLocalDataSource
    }
}