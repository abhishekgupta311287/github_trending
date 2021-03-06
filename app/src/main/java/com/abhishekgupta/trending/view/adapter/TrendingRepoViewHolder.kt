package com.abhishekgupta.trending.view.adapter

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.abhishekgupta.trending.R
import com.abhishekgupta.trending.model.RepositoryDto
import com.abhishekgupta.trending.util.parseColor
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.repository_items.view.*

class TrendingRepoViewHolder(view: View) : BaseViewHolder(view) {

    override fun bind(dto: RepositoryDto, context: Context) {

        Glide
            .with(itemView.avatar)
            .load(dto.avatar)
            .placeholder(R.drawable.placeholder_circle)
            .circleCrop()
            .into(itemView.avatar)

        itemView.author.text = dto.author
        itemView.name.text = dto.name
        itemView.description.text = dto.description

        itemView.language.text = dto.language
        itemView.stars.text = dto.stars.toString()
        itemView.forks.text = dto.forks.toString()

        setLanguageColor(dto.languageColor, ContextCompat.getColor(context, R.color.text_color))

    }

    private fun setLanguageColor(
        hexColor: String,
        @ColorInt defaultColor: Int
    ) {
        val drawableLeft = itemView.language.compoundDrawablesRelative[0]
        drawableLeft.colorFilter = PorterDuffColorFilter(
            parseColor(hexColor, defaultColor),
            PorterDuff.Mode.SRC_IN
        )
    }

}

