package com.abhishekgupta.trending.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhishekgupta.trending.R
import com.abhishekgupta.trending.model.RepositoryDto
import kotlinx.android.synthetic.main.repository_items.view.*

class TrendingRepoAdapter(private val context: Context) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private var previousExpandedPosition = -1
    private var mExpandedPosition = -1

    var repositories: ArrayList<RepositoryDto> = ArrayList()
        set(value) {
            field = value
            previousExpandedPosition = -1
            mExpandedPosition = -1
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = View.inflate(parent.context, R.layout.repository_items, null)
        return TrendingRepoViewHolder(view)
    }

    override fun getItemCount() = repositories.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(repositories[position], context)

        val isExpanded = position == mExpandedPosition
        holder.itemView.moreDetails.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.itemView.isActivated = isExpanded

        if (isExpanded) previousExpandedPosition = position

        holder.itemView.setOnClickListener {
            mExpandedPosition = if (isExpanded) -1 else position
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(position)
        }
    }

}