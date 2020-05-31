package com.example.spoonacularchatbot.features.chatbot.domain.repository

import com.example.spoonacularchatbot.core.data.local.model.QuestionEntity
import com.example.spoonacularchatbot.features.chatbot.data.model.FoodResponse
import com.example.spoonacularchatbot.features.chatbot.data.model.RecipesResponse
import com.example.spoonacularchatbot.features.splash.presentation.viewmodel.QAGraphViewModel
import io.reactivex.Single

interface ChatBotRepository {

    fun getRecipes(
        query: String,
        cuisine: String? = null,
        diet: String? = null,
        excludeIngredients: String? = null,
        intolerance: String? = null
    ): Single<RecipesResponse>

    fun detectFoodInText(text: String): Single<FoodResponse>

    fun getQAGraph(): Single<QuestionEntity>

}