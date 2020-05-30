package com.example.spoonacularchatbot.features.splash.data.repository

import com.example.spoonacularchatbot.core.data.local.cache.QuestionsLocalDataSource
import com.example.spoonacularchatbot.core.data.local.model.QuestionEntity
import com.example.spoonacularchatbot.features.splash.domain.repository.QARepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class QARepositoryImpl @Inject constructor(private val localDataSource: QuestionsLocalDataSource) :
    QARepository {
    override fun saveQAGraph(questionEntity: QuestionEntity): Completable {
        return Completable.fromCallable {
            localDataSource.saveQuestionEntity(questionEntity)
        }
    }

    override fun getQAGraph(): Single<QuestionEntity> {
        return Single.fromCallable {
            localDataSource.getQuestionEntity()
        }
    }

    override fun deleteQAGraph(): Completable {
        return Completable.fromCallable {
            localDataSource.deleteQuestionEntity()
        }
    }
}