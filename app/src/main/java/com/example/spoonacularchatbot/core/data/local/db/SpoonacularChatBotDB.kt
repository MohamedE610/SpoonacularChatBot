package com.example.spoonacularchatbot.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spoonacularchatbot.core.data.local.dao.QuestionDao
import com.example.spoonacularchatbot.core.data.local.model.QuestionEntity

@Database(entities = [QuestionEntity::class], version = 1, exportSchema = false)
@TypeConverters(DBConverters::class)
abstract class SpoonacularChatBotDB : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}