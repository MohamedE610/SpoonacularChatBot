package com.example.spoonacularchatbot.features.chatbot.domain.model

data class RecipesParams(
    val query: String,
    val cuisine: String? = null,
    val diet: String? = null,
    val excludeIngredients: String? = null,
    val intoleranceIngredients: String? = null
)