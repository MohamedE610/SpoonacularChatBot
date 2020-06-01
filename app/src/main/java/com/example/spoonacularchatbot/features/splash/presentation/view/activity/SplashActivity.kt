package com.example.spoonacularchatbot.features.splash.presentation.view.activity

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.spoonacularchatbot.R
import com.example.spoonacularchatbot.core.presentation.extensions.showShortToast
import com.example.spoonacularchatbot.core.presentation.viewmodel.ViewModelFactory
import com.example.spoonacularchatbot.features.chatbot.presentation.view.activity.ChatBotActivity
import com.example.spoonacularchatbot.features.splash.presentation.viewmodel.QAGraphViewModel
import com.example.spoonacularchatbot.features.splash.presentation.viewmodel.SplashViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<SplashViewModel>
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)
    }

    @Inject
    lateinit var qaGraphViewModelFactory: ViewModelFactory<QAGraphViewModel>
    private val qaGraphViewModel by lazy {
        ViewModelProvider(this, qaGraphViewModelFactory).get(QAGraphViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_splash)
        initViews()
        initObservers()
        viewModel.getQAGraph()
    }

    private fun initObservers() {
        viewModel.qaGraphLiveEvent.observe(this,
            Observer {
                it ?: viewModel.saveQAGraph(qaGraphViewModel.generateQAGraph())
            })
    }

    private fun initViews() {
        lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                navigateToChatBotActivity()
            }

            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}
        })
    }

    private fun navigateToChatBotActivity() {
        val intent = Intent(this, ChatBotActivity::class.java)
        startActivity(intent)
        finish()
    }
}
