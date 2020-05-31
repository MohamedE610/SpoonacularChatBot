package com.example.spoonacularchatbot.features.chatbot.data.repository

import com.example.spoonacularchatbot.core.data.local.cache.QuestionsLocalDataSource
import com.example.spoonacularchatbot.core.data.local.model.QuestionEntity
import com.example.spoonacularchatbot.features.chatbot.data.model.FoodResponse
import com.example.spoonacularchatbot.features.chatbot.data.model.RecipesResponse
import com.example.spoonacularchatbot.features.chatbot.data.source.remote.ChatBotRemoteDataSource
import com.example.spoonacularchatbot.features.chatbot.domain.repository.ChatBotRepository
import io.reactivex.Single
import javax.inject.Inject

class ChatBotRepositoryImpl @Inject constructor(
    private val remoteDataSource: ChatBotRemoteDataSource,
    private val questionsLocalDataSource: QuestionsLocalDataSource
) : ChatBotRepository {

    override fun getRecipes(
        query: String,
        cuisine: String?,
        diet: String?,
        excludeIngredients: String?,
        intolerance: String?
    ): Single<RecipesResponse> {
        return remoteDataSource.getRecipes(query, cuisine, diet, excludeIngredients, intolerance)
    }

    override fun detectFoodInText(text: String): Single<FoodResponse> {
        return remoteDataSource.detectFoodInText(text)
    }

    override fun getQAGraph(): Single<QuestionEntity> {
        return Single.fromCallable {
            questionsLocalDataSource.getQuestionEntity()
        }
    }

}