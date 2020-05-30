package com.example.spoonacularchatbot.features.chatbot.data.source.remote

import com.example.spoonacularchatbot.features.chatbot.data.model.FoodResponse
import com.example.spoonacularchatbot.features.chatbot.data.model.RecipesResponse
import io.reactivex.Single
import javax.inject.Inject

class ChatBotRemoteDataSourceImpl @Inject constructor(private val spoonacularApi: SpoonacularApi) :
    ChatBotRemoteDataSource {
    override fun getRecipes(
        query: String,
        cuisine: String?,
        diet: String?,
        excludeIngredients: String?,
        intolerance: String?
    ): Single<RecipesResponse> {
        return spoonacularApi.getRecipes(
            query,
            cuisine,
            diet,
            excludeIngredients,
            intolerance
        )
    }

    override fun detectFoodInText(text: String): Single<FoodResponse> {
        return spoonacularApi.detectFoodInText(text)
    }
}