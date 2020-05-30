package com.example.spoonacularchatbot.core.data.local.db

import androidx.room.TypeConverter
import com.example.spoonacularchatbot.core.data.local.model.Answer
import com.example.spoonacularchatbot.core.data.local.model.QUESTION_ENUM
import com.example.spoonacularchatbot.core.data.local.model.QuestionEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class DBConverters {

    @TypeConverter
    fun getRelatedQuestionJson(relatedQuestion: QuestionEntity): String? {
        val moshi = Moshi.Builder().build()
        val relatedQuestionsAdapter =
            moshi.adapter(QuestionEntity::class.java)
        return relatedQuestionsAdapter.toJson(relatedQuestion)
    }

    @TypeConverter
    fun getRelatedQuestionObject(relatedQuestionsJson: String): QuestionEntity? {
        val moshi = Moshi.Builder().build()
        val relatedQuestionsAdapter =
            moshi.adapter(QuestionEntity::class.java)
        return relatedQuestionsAdapter.fromJson(relatedQuestionsJson)
    }

    @TypeConverter
    fun getExpectedAnswersJson(expectedAnswers: List<Answer>): String? {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Answer::class.java)
        val relatedQuestionsAdapter =
            moshi.adapter<List<Answer>>(type)
        return relatedQuestionsAdapter.toJson(expectedAnswers)
    }

    @TypeConverter
    fun getExpectedAnswersObject(expectedAnswers: String): List<Answer>? {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Answer::class.java)
        val relatedQuestionsAdapter =
            moshi.adapter<List<Answer>>(type)
        return relatedQuestionsAdapter.fromJson(expectedAnswers)
    }

    @TypeConverter
    fun toQuestionEnum(value: String) = enumValueOf<QUESTION_ENUM>(value)

    @TypeConverter
    fun fromQuestionEnum(value: QUESTION_ENUM) = value.name
}