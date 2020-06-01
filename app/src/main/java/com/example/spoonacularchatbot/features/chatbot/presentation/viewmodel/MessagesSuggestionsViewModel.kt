package com.example.spoonacularchatbot.features.chatbot.presentation.viewmodel

import android.content.Context
import com.example.spoonacularchatbot.R
import com.example.spoonacularchatbot.core.data.local.model.Cuisine_Enum
import com.example.spoonacularchatbot.core.data.local.model.Vegetarian_Enum
import com.example.spoonacularchatbot.core.presentation.viewmodel.BaseViewModel
import com.example.spoonacularchatbot.features.chatbot.presentation.model.SuggestionMessage
import javax.inject.Inject

class MessagesSuggestionsViewModel @Inject constructor(private val context: Context) :
    BaseViewModel() {
    fun generateYesNoMessageSuggestions(): List<SuggestionMessage> {
        val yesMsg = SuggestionMessage(context.getString(R.string.lbl_yes), false)
        val noMsg = SuggestionMessage(context.getString(R.string.lbl_no), false)
        return arrayListOf(yesMsg, noMsg)
    }

    fun generateContinueChatMessageSuggestions(): List<SuggestionMessage> {
        val startChatMsg =
            SuggestionMessage(context.getString(R.string.start_chat_from_beginning), false)
        val askAboutRecipeMsg =
            SuggestionMessage(context.getString(R.string.ask_about_another_recipe), false)

        return arrayListOf(startChatMsg, askAboutRecipeMsg)
    }

    fun generateVegetarianTypesMessageSuggestions(): List<SuggestionMessage> {
        val suggestions = arrayListOf<SuggestionMessage>()
        for (type in Vegetarian_Enum.values())
            if (type != Vegetarian_Enum.NONE) {
                val message = SuggestionMessage(type.name, false)
                suggestions.add(message)
            }
        return suggestions
    }

    fun generateCuisineTypesMessageSuggestions(): List<SuggestionMessage> {
        val suggestions = arrayListOf<SuggestionMessage>()
        for (type in Cuisine_Enum.values()) {
            val message = SuggestionMessage(type.name, false)
            suggestions.add(message)
        }
        return suggestions
    }

}