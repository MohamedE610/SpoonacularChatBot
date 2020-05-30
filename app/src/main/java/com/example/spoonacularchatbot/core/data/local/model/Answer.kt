package com.example.spoonacularchatbot.core.data.local.model

import androidx.room.TypeConverters
import com.example.spoonacularchatbot.core.data.local.db.DBConverters
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Answer constructor(
    val id: Int,
    val text: String,
    @TypeConverters(DBConverters::class)
    val relatedQuestion: QuestionEntity
)