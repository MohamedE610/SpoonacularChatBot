package com.example.spoonacularchatbot.features.chatbot.presentation.viewmodel

import android.util.Log
import com.example.spoonacularchatbot.core.data.local.model.QuestionEntity
import com.example.spoonacularchatbot.core.data.remote.rxerrorhandling.SpoonacularException
import com.example.spoonacularchatbot.core.presentation.modelwraper.ObservableResource
import com.example.spoonacularchatbot.core.presentation.modelwraper.SingleLiveEvent
import com.example.spoonacularchatbot.core.presentation.viewmodel.BaseViewModel
import com.example.spoonacularchatbot.features.chatbot.data.model.FoodResponse
import com.example.spoonacularchatbot.features.chatbot.data.model.RecipesResponse
import com.example.spoonacularchatbot.features.chatbot.domain.interactor.DetectFoodInTextUseCase
import com.example.spoonacularchatbot.features.chatbot.domain.interactor.GetQAGraphUseCase
import com.example.spoonacularchatbot.features.chatbot.domain.interactor.GetRecipesUseCase
import com.example.spoonacularchatbot.features.chatbot.domain.model.RecipesParams
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class ChatBotViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val detectFoodInTextUseCase: DetectFoodInTextUseCase,
    private val getQAGraphUseCase: GetQAGraphUseCase
) : BaseViewModel() {

    var recipesParams: RecipesParams = RecipesParams()
    var nextQuestion: QuestionEntity? = null

    // cache last user message to use it when internet connection back
    var lastUserMessage = ""

    val qaGraphLiveEvent = SingleLiveEvent<QuestionEntity>()
    val recipesObservableResource = ObservableResource<RecipesResponse>()
    val foodInTextObservableResource = ObservableResource<FoodResponse>()

    fun getRecipes(params: RecipesParams) {
        val disposable = getRecipesUseCase.execute(params)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { recipesObservableResource.loading.postValue(true) }
            .doFinally { recipesObservableResource.loading.postValue(false) }
            .subscribe({
                recipesObservableResource.value = it
            }, {
                if (it is SpoonacularException)
                    recipesObservableResource.error.value = it
                else
                    recipesObservableResource.error.value =
                        SpoonacularException(SpoonacularException.Kind.UNEXPECTED, it)
            })

        addDisposable(disposable)
    }

    fun detectFoodInText(text: String) {
        val disposable = detectFoodInTextUseCase.execute(text)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { foodInTextObservableResource.loading.postValue(true) }
            .doFinally { foodInTextObservableResource.loading.postValue(false) }
            .subscribe({
                foodInTextObservableResource.value = it
            }, {
                if (it is SpoonacularException)
                    foodInTextObservableResource.error.value = it
                else
                    foodInTextObservableResource.error.value =
                        SpoonacularException(SpoonacularException.Kind.UNEXPECTED, it)
            })

        addDisposable(disposable)
    }

    fun getQAGraph() {
        val disposable = getQAGraphUseCase.execute()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                it?.let {
                    nextQuestion = it
                    qaGraphLiveEvent.value = it
                    Log.d("getQAGraph", "success")
                }
            }, {
                Log.d("getQAGraph", "failed")
            })

        addDisposable(disposable)
    }
}