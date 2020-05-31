package com.example.spoonacularchatbot.features.chatbot.presentation.model

import com.example.spoonacularchatbot.features.chatbot.data.model.RecipesResponse

sealed class Message
data class ChatBotMessage constructor(
    val id: Int,
    val text: String,
    val dateTime: Long
) : Message()

data class UserMessage constructor(
    val id: Int,
    val text: String,
    val dateTime: Long
) : Message()

data class RecipesMessage constructor(
    val id: Int,
    val text: String,
    val dateTime: Long,
    val recipeResponse: RecipesResponse
) : Message()


data class SuggestionMessage(
    val id: Int,
    val text: String,
    val dateTime: Long,
    var isSelected: Boolean
)