package com.example.spoonacularchatbot.core.presentation.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleBaseAdapter<T, VH : SimpleBaseViewHolder<T>> : RecyclerView.Adapter<VH>() {
    val data = arrayListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutResourceId(), parent, false)
        return getViewHolder(view)
    }

    protected abstract fun getViewHolder(view: View): VH

    @LayoutRes
    protected abstract fun getLayoutResourceId(): Int

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(data[position])
    }
}

abstract class SimpleBaseViewHolder<T> constructor(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: T)
}