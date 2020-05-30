package com.example.spoonacularchatbot.features.splash.domain.interactor

import com.example.spoonacularchatbot.features.splash.domain.repository.QARepository
import javax.inject.Inject

class GetQAGraphUseCase @Inject constructor(private val repository: QARepository) {
    fun excute() = repository.getQAGraph()
}