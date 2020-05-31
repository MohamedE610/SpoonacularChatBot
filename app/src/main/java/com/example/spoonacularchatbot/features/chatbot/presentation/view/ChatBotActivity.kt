package com.example.spoonacularchatbot.features.chatbot.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.spoonacularchatbot.BuildConfig
import com.example.spoonacularchatbot.R
import dagger.android.AndroidInjection

class ChatBotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_chat_bot)
    }
}