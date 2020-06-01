package com.example.spoonacularchatbot.features.chatbot.presentation.view.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spoonacularchatbot.R
import com.example.spoonacularchatbot.core.presentation.views.SimpleBaseAdapter
import com.example.spoonacularchatbot.core.presentation.views.SimpleBaseViewHolder
import com.example.spoonacularchatbot.features.chatbot.presentation.model.ChatBotMessage
import com.example.spoonacularchatbot.features.chatbot.presentation.model.Message
import com.example.spoonacularchatbot.features.chatbot.presentation.model.RecipesMessage
import com.example.spoonacularchatbot.features.chatbot.presentation.model.UserMessage
import kotlinx.android.synthetic.main.item_chat_bot_message.view.*
import kotlinx.android.synthetic.main.item_chat_bot_multi_message.view.*
import kotlinx.android.synthetic.main.item_user_message.view.*

class MessagesAdapter : SimpleBaseAdapter<Message, SimpleBaseViewHolder<Message>>() {

    override fun getItemViewType(position: Int): Int {
        return when {
            data[position] is ChatBotMessage -> MESSAGE.CHAT_BOT.ordinal
            data[position] is UserMessage -> MESSAGE.USER.ordinal
            data[position] is RecipesMessage -> MESSAGE.RECIPES.ordinal
            else -> MESSAGE.CHAT_BOT.ordinal
        }
    }

    override fun getViewHolder(view: View, viewType: Int): SimpleBaseViewHolder<Message> {
        return when (viewType) {
            MESSAGE.CHAT_BOT.ordinal -> ChatBotMessageViewHolder(view)
            MESSAGE.USER.ordinal -> UserMessageViewHolder(view)
            MESSAGE.RECIPES.ordinal -> RecipesMessageViewHolder(view)
            else -> ChatBotMessageViewHolder(view)
        }
    }

    override fun getLayoutResourceId(viewType: Int): Int {
        return when (viewType) {
            MESSAGE.CHAT_BOT.ordinal -> R.layout.item_chat_bot_message
            MESSAGE.USER.ordinal -> R.layout.item_user_message
            MESSAGE.RECIPES.ordinal -> R.layout.item_chat_bot_multi_message
            else -> R.layout.item_chat_bot_message
        }

    }
}

class ChatBotMessageViewHolder constructor(view: View) : SimpleBaseViewHolder<Message>(view) {
    override fun bind(item: Message) {
        if (item is ChatBotMessage) {
            itemView.tvChatBotMessage.text = item.text
        }
    }
}

class UserMessageViewHolder constructor(view: View) : SimpleBaseViewHolder<Message>(view) {
    override fun bind(item: Message) {
        if (item is UserMessage) {
            itemView.tvUserMessage.text = item.text
        }
    }
}


class RecipesMessageViewHolder constructor(view: View) :
    SimpleBaseViewHolder<Message>(view) {
    override fun bind(item: Message) {
        if (item is RecipesMessage) {
            itemView.tvChatBotMessage.text = item.text
            itemView.rvChatBotRecipes.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            val adapter = RecipesAdapter(item.recipeResponse.baseUri)
            adapter.data.addAll(item.recipeResponse.recipes)
            itemView.rvChatBotRecipes.adapter = adapter
        }
    }
}

enum class MESSAGE {
    CHAT_BOT,
    USER,
    RECIPES
}