package com.example.spoonacularchatbot.core.presentation.modelwraper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.spoonacularchatbot.core.data.remote.rxerrorhandling.SpoonacularException


class ObservableResource<T> : SingleLiveEvent<T>() {

    val error: SingleLiveEvent<SpoonacularException> = ErrorLiveData()
    val loading: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun observe(
        owner: LifecycleOwner, successObserver: Observer<T>,
        loadingObserver: Observer<Boolean>? = null,
        commonErrorObserver: Observer<SpoonacularException>,
        httpErrorConsumer: Observer<SpoonacularException>? = null,
        networkErrorConsumer: Observer<SpoonacularException>? = null,
        unExpectedErrorConsumer: Observer<SpoonacularException>? = null,
        serverDownErrorConsumer: Observer<SpoonacularException>? = null,
        timeOutErrorConsumer: Observer<SpoonacularException>? = null,
        unAuthorizedErrorConsumer: Observer<SpoonacularException>? = null
    ) {
        super.observe(owner, successObserver)
        loadingObserver?.let { loading.observe(owner, it) }
        (error as ErrorLiveData).observe(
            owner,
            commonErrorObserver,
            httpErrorConsumer,
            networkErrorConsumer,
            unExpectedErrorConsumer,
            serverDownErrorConsumer,
            timeOutErrorConsumer,
            unAuthorizedErrorConsumer
        )
    }


    class ErrorLiveData : SingleLiveEvent<SpoonacularException>() {
        private var ownerRef: LifecycleOwner? = null
        private var httpErrorConsumer: Observer<SpoonacularException>? = null
        private var networkErrorConsumer: Observer<SpoonacularException>? = null
        private var unExpectedErrorConsumer: Observer<SpoonacularException>? = null
        private var commonErrorConsumer: Observer<SpoonacularException>? = null

        private var serverDownErrorConsumer: Observer<SpoonacularException>? = null
        private var timeOutErrorConsumer: Observer<SpoonacularException>? = null
        private var unAuthorizedErrorConsumer: Observer<SpoonacularException>? = null

        override fun setValue(value: SpoonacularException?) {
            ownerRef?.also {
                removeObservers(it)
                value?.let { vale -> addProperObserver(vale) }
                super.setValue(value)
            }

        }

        override fun postValue(value: SpoonacularException) {
            ownerRef?.also {
                removeObservers(it)
                addProperObserver(value)
                super.postValue(value)
            }

        }

        private fun addProperObserver(value: SpoonacularException) {
            when (value.kind) {
                SpoonacularException.Kind.NETWORK -> networkErrorConsumer?.let {
                    observe(
                        ownerRef!!,
                        it
                    )
                }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)
                SpoonacularException.Kind.HTTP -> httpErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)
                SpoonacularException.Kind.UNEXPECTED -> unExpectedErrorConsumer?.let {
                    observe(
                        ownerRef!!,
                        it
                    )
                }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                SpoonacularException.Kind.SERVER_DOWN -> serverDownErrorConsumer?.let {
                    observe(
                        ownerRef!!,
                        it
                    )
                }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                SpoonacularException.Kind.TIME_OUT -> timeOutErrorConsumer?.let {
                    observe(
                        ownerRef!!,
                        it
                    )
                }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                SpoonacularException.Kind.UNAUTHORIZED -> unAuthorizedErrorConsumer?.let {
                    observe(
                        ownerRef!!,
                        it
                    )
                }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                else -> {
                }
            }
        }


        fun observe(
            owner: LifecycleOwner, commonErrorConsumer: Observer<SpoonacularException>,
            httpErrorConsumer: Observer<SpoonacularException>? = null,
            networkErrorConsumer: Observer<SpoonacularException>? = null,
            unExpectedErrorConsumer: Observer<SpoonacularException>? = null,

            serverDownErrorConsumer: Observer<SpoonacularException>? = null,
            timeOutErrorConsumer: Observer<SpoonacularException>? = null,
            unAuthorizedErrorConsumer: Observer<SpoonacularException>? = null
        ) {
            super.observe(owner, commonErrorConsumer)
            this.ownerRef = owner
            this.commonErrorConsumer = commonErrorConsumer
            this.httpErrorConsumer = httpErrorConsumer
            this.networkErrorConsumer = networkErrorConsumer
            this.unExpectedErrorConsumer = unExpectedErrorConsumer
            this.serverDownErrorConsumer = serverDownErrorConsumer
            this.timeOutErrorConsumer = timeOutErrorConsumer
            this.unAuthorizedErrorConsumer = unAuthorizedErrorConsumer
        }
    }
}