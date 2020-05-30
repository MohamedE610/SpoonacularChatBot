package com.example.spoonacularchatbot.features.splash.domain.repository

import com.example.spoonacularchatbot.core.data.local.model.QuestionEntity
import io.reactivex.Completable
import io.reactivex.Single

/*QARepository
QA -> Questions and answers */
interface QARepository {
    fun saveQAGraph(questionEntity: QuestionEntity): Completable
    fun getQAGraph(): Single<QuestionEntity>
    fun deleteQAGraph(): Completable
}