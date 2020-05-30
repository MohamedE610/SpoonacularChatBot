package com.example.spoonacularchatbot.features.chatbot.domain.interactor

import com.example.spoonacularchatbot.features.chatbot.domain.model.RecipesParams
import com.example.spoonacularchatbot.features.chatbot.domain.repository.ChatBotRepository
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(private val repository: ChatBotRepository) {

    fun execute(params: RecipesParams) = repository.getRecipes(
        params.query,
        params.cuisine,
        params.diet,
        params.excludeIngredients,
        params.intoleranceIngredients
    )
}