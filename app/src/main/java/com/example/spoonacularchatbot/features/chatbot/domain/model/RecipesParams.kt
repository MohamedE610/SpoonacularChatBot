package com.example.spoonacularchatbot.features.chatbot.domain.model

data class RecipesParams(
    var query: String = "",
    var cuisine: String? = null,
    var diet: String? = null,
    var excludeIngredients: String? = null,
    var intoleranceIngredients: String? = null
)