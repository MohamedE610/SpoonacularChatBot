package com.example.spoonacularchatbot.features.chatbot.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.spoonacularchatbot.R
import com.example.spoonacularchatbot.core.data.local.model.QuestionEntity
import com.example.spoonacularchatbot.core.data.remote.rxerrorhandling.SpoonacularException
import com.example.spoonacularchatbot.core.presentation.viewmodel.ViewModelFactory
import com.example.spoonacularchatbot.features.chatbot.data.model.FoodResponse
import com.example.spoonacularchatbot.features.chatbot.data.model.RecipesResponse
import com.example.spoonacularchatbot.features.chatbot.presentation.viewmodel.ChatBotViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class ChatBotActivity : AppCompatActivity() {

    @Inject
    lateinit var chatBotViewModelFactory: ViewModelFactory<ChatBotViewModel>

    private val chatBotViewModel by lazy {
        ViewModelProvider(this, chatBotViewModelFactory).get(ChatBotViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_chat_bot)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        observeOnQAGraph()
        observeOnFetchRecipes()
        observeOnDetectFoodInText()
    }

    private fun observeOnDetectFoodInText() {
        chatBotViewModel.foodInTextObservableResource.observe(
            this,
            successObserver = Observer { it?.let { foodInTextDetectedSuccessfully(it) } },
            loadingObserver = Observer { it?.let { showLoading(it) } },
            commonErrorObserver = Observer { it?.let { showError(it) } }
        )
    }

    private fun foodInTextDetectedSuccessfully(it: FoodResponse) {

    }

    private fun observeOnFetchRecipes() {
        chatBotViewModel.recipesObservableResource.observe(
            this,
            successObserver = Observer { it?.let { recipesFetchedSuccessfully(it) } },
            loadingObserver = Observer { it?.let { showLoading(it) } },
            commonErrorObserver = Observer { it?.let { showError(it) } }
        )
    }

    private fun recipesFetchedSuccessfully(it: RecipesResponse) {

    }

    private fun showLoading(it: Boolean) {

    }

    private fun showError(it: SpoonacularException) {

    }

    private fun observeOnQAGraph() {
        chatBotViewModel.qaGraphLiveEvent.observe(this, Observer {
            it?.let {
                startChat(it)
            }
        })
    }

    private fun startChat(questionEntity: QuestionEntity) {

    }

    private fun initViews() {

    }
}