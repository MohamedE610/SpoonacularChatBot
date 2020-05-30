package com.example.spoonacularchatbot.features.chatbot.domain.interactor

import com.example.spoonacularchatbot.features.chatbot.domain.repository.ChatBotRepository
import javax.inject.Inject

class DetectFoodInTextUseCase @Inject constructor(private val repository: ChatBotRepository) {

    fun execute(text: String) = repository.detectFoodInText(text)
}