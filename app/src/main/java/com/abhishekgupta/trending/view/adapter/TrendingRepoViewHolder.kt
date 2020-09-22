package com.abhishekgupta.trending.view.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.view.View
import androidx.annotation.ColorInt
import com.abhishekgupta.trending.R
import com.abhishekgupta.trending.model.RepositoryDto
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.repository_items.view.*

class TrendingRepoViewHolder(view: View) : BaseViewHolder(view) {

    override fun bind(dto: RepositoryDto, context: Context) {

        Glide
            .with(itemView.avatar)
            .load(dto.avatar)
            .circleCrop()
            .into(itemView.avatar)

        itemView.author.text = dto.author
        itemView.name.text = dto.name
        itemView.description.text = dto.description

        itemView.language.text = dto.language
        itemView.stars.text = dto.stars.toString()
        itemView.forks.text = dto.forks.toString()


        val defaultColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.resources.getColor(R.color.text_color, null)
        } else {
            context.resources.getColor(R.color.text_color)
        }
        setLanguageColor(dto.languageColor, defaultColor)

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

    @ColorInt
    private fun parseColor(hexColor: String?, @ColorInt defaultColor: Int): Int {
        hexColor ?: return defaultColor
        return try {
            Color.parseColor(hexColor)
        } catch (e: Exception) {
            // NullPointer, IndexOutOfBounds, IllegalArgument
            defaultColor
        }
    }

}

