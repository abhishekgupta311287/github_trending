package com.abhishekgupta.trending.view.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.abhishekgupta.trending.model.RepositoryDto

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(dto: RepositoryDto, context: Context)
}