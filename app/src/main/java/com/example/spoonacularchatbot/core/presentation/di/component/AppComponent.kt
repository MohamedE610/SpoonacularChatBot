package com.example.spoonacularchatbot.core.presentation.di.component

import android.app.Application
import com.example.spoonacularchatbot.core.presentation.application.SpoonacularChatBotApp
import com.example.spoonacularchatbot.core.presentation.di.module.ActivityBuilder
import com.example.spoonacularchatbot.core.presentation.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilder::class,
        AppModule::class
    ]
)
interface AppComponent {
    fun inject(app: SpoonacularChatBotApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}
