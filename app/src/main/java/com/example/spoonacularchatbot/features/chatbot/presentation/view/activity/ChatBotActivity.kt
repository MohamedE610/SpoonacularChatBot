package com.example.spoonacularchatbot.features.chatbot.presentation.view.activity

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spoonacularchatbot.R
import com.example.spoonacularchatbot.core.data.local.model.QUESTION_ENUM
import com.example.spoonacularchatbot.core.data.local.model.QuestionEntity
import com.example.spoonacularchatbot.core.data.local.model.Vegetarian_Enum
import com.example.spoonacularchatbot.core.presentation.common.InternetConnectivityListener
import com.example.spoonacularchatbot.core.presentation.extensions.isInternetAvailable
import com.example.spoonacularchatbot.core.presentation.extensions.showShortToast
import com.example.spoonacularchatbot.core.presentation.viewmodel.ViewModelFactory
import com.example.spoonacularchatbot.features.chatbot.data.model.FoodResponse
import com.example.spoonacularchatbot.features.chatbot.data.model.RecipesResponse
import com.example.spoonacularchatbot.features.chatbot.presentation.model.*
import com.example.spoonacularchatbot.features.chatbot.presentation.view.adapter.MessageSuggestionAdapter
import com.example.spoonacularchatbot.features.chatbot.presentation.view.adapter.MessagesAdapter
import com.example.spoonacularchatbot.features.chatbot.presentation.viewmodel.ChatBotViewModel
import com.example.spoonacularchatbot.features.chatbot.presentation.viewmodel.UserAnswerViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_chat_bot.*
import java.util.*
import javax.inject.Inject

class ChatBotActivity : AppCompatActivity() {

    @Inject
    lateinit var internetConnectivityListener: InternetConnectivityListener

    @Inject
    lateinit var chatBotViewModelFactory: ViewModelFactory<ChatBotViewModel>

    private val chatBotViewModel by lazy {
        ViewModelProvider(this, chatBotViewModelFactory).get(ChatBotViewModel::class.java)
    }

    @Inject
    lateinit var userAnswerViewModelFactory: ViewModelFactory<UserAnswerViewModel>

    private val userAnswerViewModel by lazy {
        ViewModelProvider(this, userAnswerViewModelFactory).get(UserAnswerViewModel::class.java)
    }

    private val messagesAdapter = MessagesAdapter()
    private val messageSuggestionAdapter = MessageSuggestionAdapter(::onMessageSuggestItemClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_chat_bot)
        initViews()
        initObservers()
        loadQAGraph()
    }

    private fun loadQAGraph() {
        chatBotViewModel.getQAGraph()
    }

    // region initObservers
    private fun initObservers() {
        observeOnQAGraph()
        observeOnFetchRecipes()
        observeOnDetectFoodInText()
        observeOnInternetConnection()
    }

    private fun observeOnInternetConnection() {
        internetConnectivityListener.internetConnectivityLiveData.observe(this, Observer {
            it?.let { isInternetConnected ->
                if (isInternetConnected)
                    if (chatBotViewModel.lastUserMessage.isNotEmpty())
                        checkUserMessage(chatBotViewModel.lastUserMessage)
            }
        })
    }

    private fun observeOnDetectFoodInText() {
        chatBotViewModel.foodInTextObservableResource.observe(
            this,
            successObserver = Observer { it?.let { foodInTextDetectedSuccessfully(it) } },
            loadingObserver = Observer { it?.let { showLoading(it) } },
            commonErrorObserver = Observer { it?.let { showError() } }
        )
    }

    private fun foodInTextDetectedSuccessfully(it: FoodResponse) {
        val answer: String
        when (chatBotViewModel.nextQuestion?.type) {
            QUESTION_ENUM.MAIN_INGREDIENTS, QUESTION_ENUM.VEGETARIAN_MAIN_INGREDIENTS -> {
                answer = it.foods.joinToString(" ") { food -> food.annotation }
                chatBotViewModel.recipesParams.query = answer

                chatBotViewModel.nextQuestion =
                    chatBotViewModel.nextQuestion?.expectedAnswers!![0].relatedQuestion
                chatBotViewModel.nextQuestion?.let { sendChatBotMessage(it) }
            }
            QUESTION_ENUM.EXCLUDE_INGREDIENTS -> {
                answer = it.foods.joinToString(",") { food -> food.annotation }
                if (answer.isNotEmpty())
                    chatBotViewModel.recipesParams.excludeIngredients = answer

                chatBotViewModel.nextQuestion =
                    chatBotViewModel.nextQuestion?.expectedAnswers!![0].relatedQuestion
                chatBotViewModel.nextQuestion?.let { sendChatBotMessage(it) }
            }
            QUESTION_ENUM.INTOLERANCE_INGREDIENTS -> {
                answer = it.foods.joinToString(",") { food -> food.annotation }
                if (answer.isNotEmpty())
                    chatBotViewModel.recipesParams.intoleranceIngredients = answer

                // this is the last question and i have all info i need getRecipes()
                chatBotViewModel.getRecipes(chatBotViewModel.recipesParams)
            }
            else -> Unit
        }

    }

    private fun observeOnFetchRecipes() {
        chatBotViewModel.recipesObservableResource.observe(
            this,
            successObserver = Observer { it?.let { recipesFetchedSuccessfully(it) } },
            loadingObserver = Observer { it?.let { showLoading(it) } },
            commonErrorObserver = Observer {
                it?.let {
                    showError()
                    sendChatBotMessage(getString(R.string.empty_recipe_message))
                    startAskQuestionFromMainIngredients()
                }
            }
        )
    }

    private fun recipesFetchedSuccessfully(it: RecipesResponse) {
        sendRecipesMessage(it)
    }

    private fun observeOnQAGraph() {
        chatBotViewModel.qaGraphLiveEvent.observe(this, Observer {
            it?.let {
                startChat(it)
            }
        })
    }

    private fun showLoading(it: Boolean) {

    }

    private fun showError() {
        if (isInternetAvailable())
            showShortToast(getString(R.string.general_error_msg))
        else
            showShortToast(getString(R.string.network_error_msg))
    }

    private fun startChat(questionEntity: QuestionEntity) {
        sendChatBotMessage(questionEntity)
    }

    // endregion initObservers

    //region initViews
    private fun initViews() {
        setUpToolbar()
        setUpMessagesRecyclerViews()
        setUpMessagesSuggestionRecyclerView()
        fabChatBotSend.setOnClickListener {
            fabChatBotSendClicked()
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolBarChatBot)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun fabChatBotSendClicked() {
        if (TextUtils.isEmpty(etChatBot.text))
            return

        val message = etChatBot.text.toString()
        chatBotViewModel.lastUserMessage = message
        etChatBot.setText("")
        sendUserMessage(message)
        checkUserMessage(message)
    }

    private fun setUpMessagesSuggestionRecyclerView() {
        rvChatBotSuggestions.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvChatBotSuggestions.adapter = messageSuggestionAdapter
    }

    private fun setUpMessagesRecyclerViews() {
        rvChatBotMessages.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvChatBotMessages.adapter = messagesAdapter
    }
    //endregion initViews

    // region sendMessage

    private fun sendRecipesMessage(it: RecipesResponse) {
        //in case no recipes with user ingredients and requests
        if (it.recipes.isEmpty()) {
            sendChatBotMessage(getString(R.string.empty_recipe_message))
            startAskQuestionFromMainIngredients()
            return
        }

        val dateTime = Calendar.getInstance().timeInMillis
        val message = RecipesMessage(
            id = dateTime.toInt(),
            text = getString(R.string.recipe_message),
            recipeResponse = it,
            dateTime = dateTime
        )
        sendMessage(message)

        chatBotViewModel.nextQuestion =
            chatBotViewModel.nextQuestion?.expectedAnswers!![0].relatedQuestion

        chatBotViewModel.nextQuestion?.let {
            Handler().postDelayed({ sendChatBotMessage(it) }, DELAY_6000MS)
        }
    }

    private fun sendChatBotMessage(questionEntity: QuestionEntity) {
        val dateTime = Calendar.getInstance().timeInMillis
        val messages = questionEntity.text.split("-")
        for ((i, msg) in messages.withIndex()) {
            val message =
                ChatBotMessage(id = dateTime.toInt(), text = msg.trim(), dateTime = dateTime)

            if (messages.size > 1)
                Handler().postDelayed({ sendMessage(message) }, DELAY_500MS * (i + 1))
            else
                Handler().postDelayed({ sendMessage(message) }, DELAY_1000MS)
        }
    }

    private fun sendChatBotMessage(text: String) {
        val dateTime = Calendar.getInstance().timeInMillis
        val messages = text.split("-")
        for ((i, msg) in messages.withIndex()) {
            val message =
                ChatBotMessage(id = dateTime.toInt(), text = msg.trim(), dateTime = dateTime)

            if (messages.size > 1)
                Handler().postDelayed({ sendMessage(message) }, DELAY_500MS * (i + 1))
            else
                Handler().postDelayed({ sendMessage(message) }, DELAY_1000MS)
        }
    }

    private fun sendUserMessage(msg: String) {
        val dateTime = Calendar.getInstance().timeInMillis
        val message = UserMessage(id = dateTime.toInt(), text = msg.trim(), dateTime = dateTime)
        sendMessage(message)
    }

    private fun sendMessage(message: Message) {
        if (isDestroyed)
            return

        messagesAdapter.data.add(message)
        messagesAdapter.notifyDataSetChanged()
        rvChatBotMessages?.scrollToPosition(messagesAdapter.data.size - 1)
    }

    // endregion sendMessage

    // region check user answers

    private fun checkUserMessage(message: String) {
        if (chatBotViewModel.nextQuestion?.expectedAnswers?.size == 0)
            return

        when (chatBotViewModel.nextQuestion?.type) {
            QUESTION_ENUM.WHATS_UR_NAME -> {
                getUserNameFromMessage(message)
            }
            QUESTION_ENUM.R_U_VEGETARIAN -> {
                checkAreYouVegetarianAnswer(message)
            }
            QUESTION_ENUM.MAIN_INGREDIENTS, QUESTION_ENUM.VEGETARIAN_MAIN_INGREDIENTS -> {
                checkMainIngredientsAnswer(message)
            }
            QUESTION_ENUM.VEGETARIAN_TYPE -> {
                checkVegetarianTypeAnswer(message)
            }
            QUESTION_ENUM.CUISINE -> {
                checkCuisineAnswer(message)
            }
            QUESTION_ENUM.EXCLUDE_INGREDIENTS -> {
                checkExcludeIngredientsAnswer(message)
            }
            QUESTION_ENUM.INTOLERANCE_INGREDIENTS -> {
                checkIntoleranceIngredientsAnswer(message)
            }
            QUESTION_ENUM.DO_YOU_LIKE_IT -> {
                checkDoYouLikeItAnswer(message)
            }
            QUESTION_ENUM.NONE -> return
            else -> return
        }
    }


    private fun checkDoYouLikeItAnswer(message: String) {
        val isUserLikeRecipes = userAnswerViewModel.checkDoYouLikeItAnswer(message)
        if (isUserLikeRecipes) {
            sendChatBotMessage(getString(R.string.do_you_like_it_yes))
            chatBotViewModel.nextQuestion = null
        } else {
            sendChatBotMessage(getString(R.string.do_you_like_it_no))
            startAskQuestionFromMainIngredients()
        }
    }

    private fun startAskQuestionFromMainIngredients() {
        chatBotViewModel.nextQuestion = chatBotViewModel.qaGraphLiveEvent.value
        if (chatBotViewModel.nextQuestion == null)
            return

        val isUserVegetarian = !chatBotViewModel.recipesParams.diet.isNullOrEmpty()

        while (true) {

            if (chatBotViewModel.nextQuestion?.type == QUESTION_ENUM.R_U_VEGETARIAN) {
                if (isUserVegetarian) {
                    // index 0 for "yes" answer
                    chatBotViewModel.nextQuestion =
                        chatBotViewModel.nextQuestion?.expectedAnswers!![0].relatedQuestion // which kind of vegetarian ?

                    chatBotViewModel.nextQuestion =
                        chatBotViewModel.nextQuestion?.expectedAnswers!![0].relatedQuestion // main ingredient question for vegetarians
                } else
                // index 1 for "no" answer
                    chatBotViewModel.nextQuestion =
                        chatBotViewModel.nextQuestion?.expectedAnswers!![1].relatedQuestion // main ingredient question

                break
            }

            chatBotViewModel.nextQuestion =
                chatBotViewModel.nextQuestion?.expectedAnswers!![0].relatedQuestion
        }

        Handler().postDelayed({
            chatBotViewModel.nextQuestion?.let { sendChatBotMessage(it) }
        }, DELAY_1000MS)
    }

    private fun checkExcludeIngredientsAnswer(message: String) {
        chatBotViewModel.detectFoodInText(message)
    }

    private fun checkIntoleranceIngredientsAnswer(message: String) {
        chatBotViewModel.detectFoodInText(message)
    }

    private fun checkCuisineAnswer(message: String) {
        val answer = userAnswerViewModel.checkCuisineAnswer(message)
        if (answer.isNotEmpty())
            chatBotViewModel.recipesParams.cuisine = answer
        chatBotViewModel.nextQuestion =
            chatBotViewModel.nextQuestion?.expectedAnswers!![0].relatedQuestion
        chatBotViewModel.nextQuestion?.let { sendChatBotMessage(it) }
    }

    private fun checkMainIngredientsAnswer(message: String) {
        chatBotViewModel.detectFoodInText(message)
    }

    private fun checkVegetarianTypeAnswer(message: String) {
        val answer = userAnswerViewModel.checkVegetarianTypeAnswer(message)
        if (answer == Vegetarian_Enum.NONE) {
            //TODO wrong answer
        } else {
            chatBotViewModel.recipesParams.diet = answer.name
            chatBotViewModel.nextQuestion =
                chatBotViewModel.nextQuestion?.expectedAnswers!![0].relatedQuestion
            chatBotViewModel.nextQuestion?.let { sendChatBotMessage(it) }
        }
    }

    private fun checkAreYouVegetarianAnswer(message: String) {
        val answer = userAnswerViewModel.checkAreYouVegetarianAnswer(message)

        when (answer) {
            YES_NO.YES -> {
                chatBotViewModel.nextQuestion =
                    chatBotViewModel.nextQuestion?.expectedAnswers!![0].relatedQuestion // index 0 for "yes" answer
                chatBotViewModel.nextQuestion?.let { sendChatBotMessage(it) }
            }
            YES_NO.NO -> {
                chatBotViewModel.nextQuestion =
                    chatBotViewModel.nextQuestion?.expectedAnswers!![1].relatedQuestion// index 1 for "no" answer
                chatBotViewModel.nextQuestion?.let { sendChatBotMessage(it) }
            }
            else -> {
                //TODO wrong answer
            }
        }

    }

    private fun getUserNameFromMessage(message: String) {
        val username = userAnswerViewModel.getUserNameFromMessage(message)

        chatBotViewModel.nextQuestion =
            chatBotViewModel.nextQuestion?.expectedAnswers!![0].relatedQuestion

        // add user name to a next chat bot message
        chatBotViewModel.nextQuestion?.text = getString(R.string.are_you_vegetarian, username)

        chatBotViewModel.nextQuestion?.let { sendChatBotMessage(it) }
    }


    private fun onMessageSuggestItemClicked(message: SuggestionMessage) {

    }

    // endregion check user answers


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        internetConnectivityListener.registerInternetReceiver()
        if (messagesAdapter.data.isNotEmpty())
            rvChatBotMessages?.scrollToPosition(messagesAdapter.data.size - 1)
    }

    override fun onPause() {
        super.onPause()
        internetConnectivityListener.unregisterInternetReceiver()
    }

    companion object {
        const val DELAY_500MS: Long = 500
        const val DELAY_1000MS: Long = 1000
        const val DELAY_6000MS: Long = 6000
    }
}