package com.example.spoonacularchatbot.features.splash.domain.interactor

import com.example.spoonacularchatbot.features.splash.domain.repository.QARepository
import javax.inject.Inject

class DeleteQAGraphUseCase @Inject constructor(private val repository: QARepository) {
    fun execute() = repository.deleteQAGraph()
}