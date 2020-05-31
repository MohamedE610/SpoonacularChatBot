package com.example.spoonacularchatbot.features.splash.presentation.viewmodel

import android.util.Log
import com.example.spoonacularchatbot.core.data.local.model.QuestionEntity
import com.example.spoonacularchatbot.core.presentation.modelwraper.SingleLiveEvent
import com.example.spoonacularchatbot.core.presentation.viewmodel.BaseViewModel
import com.example.spoonacularchatbot.features.splash.domain.interactor.DeleteQAGraphUseCase
import com.example.spoonacularchatbot.features.splash.domain.interactor.GetQAGraphUseCase
import com.example.spoonacularchatbot.features.splash.domain.interactor.SaveQAGraphUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.NullPointerException
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val getQAGraphUseCase: GetQAGraphUseCase,
    private val saveQAGraphUseCase: SaveQAGraphUseCase,
    private val deleteQAGraphUseCase: DeleteQAGraphUseCase
) : BaseViewModel() {

    val qaGraphLiveEvent = SingleLiveEvent<QuestionEntity>()

    fun saveQAGraph(questionEntity: QuestionEntity) {
        val disposable = saveQAGraphUseCase.execute(questionEntity)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("saveQAGraph", "success")
            }, {
                Log.d("saveQAGraph", "failed")
            })

        addDisposable(disposable)
    }

    fun deleteQAGraph() {
        val disposable = deleteQAGraphUseCase.execute()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("deleteQAGraph", "success")
            }, {
                Log.d("deleteQAGraph", "failed")
            })

        addDisposable(disposable)
    }


    fun getQAGraph() {
        val disposable = getQAGraphUseCase.execute()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                it?.let {
                    qaGraphLiveEvent.value = it
                    Log.d("getQAGraph", "success")
                }
            }, {
                if (it is NullPointerException)
                    qaGraphLiveEvent.value = null
                Log.d("getQAGraph", "failed")
            })

        addDisposable(disposable)
    }

}