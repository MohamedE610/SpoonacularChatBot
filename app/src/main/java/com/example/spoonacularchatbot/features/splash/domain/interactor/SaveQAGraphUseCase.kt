package com.example.spoonacularchatbot.features.splash.domain.interactor

import com.example.spoonacularchatbot.core.data.local.model.QuestionEntity
import com.example.spoonacularchatbot.features.splash.domain.repository.QARepository
import javax.inject.Inject

class SaveQAGraphUseCase @Inject constructor(private val repository: QARepository) {
    fun execute(questionEntity: QuestionEntity) = repository.saveQAGraph(questionEntity)
}