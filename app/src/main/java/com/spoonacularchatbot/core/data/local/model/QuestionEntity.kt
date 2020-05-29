package com.spoonacularchatbot.core.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.spoonacularchatbot.core.data.local.db.DBConverters

@Entity(tableName = "QuestionEntity")
data class QuestionEntity constructor(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "text")
    val text: String,
    @TypeConverters(DBConverters::class)
    @ColumnInfo(name = "relatedQuestions")
    val relatedQuestions: List<QuestionEntity>
)