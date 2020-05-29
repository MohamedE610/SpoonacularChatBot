package com.example.spoonacularchatbot.core.data.local.cache

import com.example.spoonacularchatbot.core.data.local.model.QuestionEntity

interface QuestionsLocalDataSource {
    fun saveQuestionEntity(questionEntity: QuestionEntity)
    fun deleteQuestionEntity()
    fun getQuestionEntity(): QuestionEntity
}