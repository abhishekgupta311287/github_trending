package com.abhishekgupta.trending.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhishekgupta.trending.R
import com.abhishekgupta.trending.model.RepositoryDto

class TrendingRepoAdapter(private val context: Context) :
    RecyclerView.Adapter<BaseViewHolder>() {
    var repositories: ArrayList<RepositoryDto> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = View.inflate(parent.context, R.layout.repository_items, null)
        return TrendingRepoViewHolder(view)
    }

    override fun getItemCount() = repositories.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(repositories[position], context)
    }

}