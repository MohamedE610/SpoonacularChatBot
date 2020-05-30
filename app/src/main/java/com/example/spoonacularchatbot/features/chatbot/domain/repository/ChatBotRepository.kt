package com.example.spoonacularchatbot.features.chatbot.domain.repository

import com.example.spoonacularchatbot.features.chatbot.data.model.FoodResponse
import com.example.spoonacularchatbot.features.chatbot.data.model.RecipesResponse
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
}