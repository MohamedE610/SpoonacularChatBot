package com.emiratesauction.spoonacularchatbot.core.data.local.cache

import com.emiratesauction.spoonacularchatbot.core.data.local.dao.QuestionDao
import com.emiratesauction.spoonacularchatbot.core.data.local.model.QuestionEntity
import javax.inject.Inject

class QuestionsLocalDataSourceImpl @Inject constructor(private val questionDao: QuestionDao) :
    QuestionsLocalDataSource {
    override fun saveQuestionEntity(questionEntity: QuestionEntity) {
        questionDao.insertQuestionEntity(questionEntity)
    }

    override fun deleteQuestionEntity() {
        questionDao.deleteDeliveryRequest()
    }

    override fun getQuestionEntity(): QuestionEntity {
        return questionDao.getDeliveryRequest()
    }

}