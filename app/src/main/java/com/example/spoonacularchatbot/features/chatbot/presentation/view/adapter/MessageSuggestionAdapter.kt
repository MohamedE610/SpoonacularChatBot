package com.example.spoonacularchatbot.features.chatbot.presentation.view.adapter

import android.view.View
import androidx.core.content.ContextCompat
import com.example.spoonacularchatbot.R
import com.example.spoonacularchatbot.core.presentation.views.SimpleBaseAdapter
import com.example.spoonacularchatbot.core.presentation.views.SimpleBaseViewHolder

import com.example.spoonacularchatbot.features.chatbot.presentation.model.SuggestionMessage
import kotlinx.android.synthetic.main.item_message_suggestions.view.*

class MessageSuggestionAdapter constructor(private val onItemClicked: (message: SuggestionMessage) -> Unit) :
    SimpleBaseAdapter<SuggestionMessage, MessageSuggestionAdapter.MessageSuggestionViewHolder>() {

    override fun getViewHolder(view: View, viewType: Int): MessageSuggestionViewHolder {
        return MessageSuggestionViewHolder(view)
    }

    override fun getLayoutResourceId(viewType: Int): Int {
        return R.layout.item_message_suggestions
    }

    inner class MessageSuggestionViewHolder constructor(view: View) :
        SimpleBaseViewHolder<SuggestionMessage>(view) {

        private var item: SuggestionMessage? = null

        init {
            itemView.setOnClickListener {

                val isItemSelected = item?.isSelected ?: false
                if (isItemSelected)
                    itemView.tvMessageSuggestion.background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.bg_message_suggestion_selected
                    )
                else
                    itemView.tvMessageSuggestion.background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.bg_message_suggestion
                    )

                item?.isSelected = !isItemSelected
                item?.let { onItemClicked(it) }
            }
        }

        override fun bind(item: SuggestionMessage) {
            this.item = item
            itemView.tvMessageSuggestion.text = item.text
        }
    }
}