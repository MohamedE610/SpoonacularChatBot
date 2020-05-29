package com.example.spoonacularchatbot.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spoonacularchatbot.core.data.local.model.QuestionEntity

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestionEntity(questionEntity: QuestionEntity)

    @Query("select * from QuestionEntity limit 1")
    fun getDeliveryRequest(): QuestionEntity

    @Query("delete from  QuestionEntity")
    fun deleteDeliveryRequest()
}