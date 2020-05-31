package com.example.spoonacularchatbot.features.chatbot.presentation.view.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.example.spoonacularchatbot.R
import com.example.spoonacularchatbot.core.presentation.extensions.loadFromUrl
import com.example.spoonacularchatbot.core.presentation.views.SimpleBaseAdapter
import com.example.spoonacularchatbot.core.presentation.views.SimpleBaseViewHolder
import com.example.spoonacularchatbot.features.chatbot.data.model.RecipesResponse
import kotlinx.android.synthetic.main.item_recipe.view.*


class RecipesAdapter(private val baseUrl: String) : SimpleBaseAdapter<RecipesResponse.Recipe, RecipesAdapter.RecipeViewHolder>(){

    override fun getViewHolder(view: View, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(view)
    }

    override fun getLayoutResourceId(viewType: Int): Int {
        return R.layout.item_recipe
    }

    inner class RecipeViewHolder constructor(view: View) :
        SimpleBaseViewHolder<RecipesResponse.Recipe>(view) {
        private var item: RecipesResponse.Recipe? = null

        init {
            itemView.setOnClickListener {
                openUrlInWebBrowser(itemView.context, item?.sourceUrl ?: "404")
            }
        }

        private fun openUrlInWebBrowser(ctx: Context, sourceUrl: String) {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl))
            startActivity(ctx, browserIntent, null)
        }

        override fun bind(item: RecipesResponse.Recipe) {
            this.item = item
            itemView.imgRecipe.loadFromUrl("$baseUrl${item.image}")
            itemView.tvRecipeName.text = item.title
        }
    }

}