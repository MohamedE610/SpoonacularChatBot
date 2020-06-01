package com.example.spoonacularchatbot.core.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.spoonacularchatbot.core.data.local.db.DBConverters
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "QuestionEntity")
data class QuestionEntity constructor(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "text")
    var text: String,
    @TypeConverters(DBConverters::class)
    @ColumnInfo(name = "type")
    val type: QUESTION_ENUM,
    @TypeConverters(DBConverters::class)
    @ColumnInfo(name = "expectedAnswers")
    val expectedAnswers: List<Answer>
)

