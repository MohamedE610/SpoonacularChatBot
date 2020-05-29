package com.example.spoonacularchatbot.core.data.local.db

import androidx.room.TypeConverter
import com.example.spoonacularchatbot.core.data.local.model.QuestionEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class DBConverters {

    @TypeConverter
    fun getRelatedQuestionJson(relatedQuestions: List<QuestionEntity>): String? {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, QuestionEntity::class.java)
        val relatedQuestionsAdapter =
            moshi.adapter<List<QuestionEntity>>(type)
        return relatedQuestionsAdapter.toJson(relatedQuestions)
    }

    @TypeConverter
    fun getRelatedQuestionObject(relatedQuestionsJson: String): List<QuestionEntity>? {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, QuestionEntity::class.java)
        val relatedQuestionsAdapter =
            moshi.adapter<List<QuestionEntity>>(type)
        return relatedQuestionsAdapter.fromJson(relatedQuestionsJson)
    }
}