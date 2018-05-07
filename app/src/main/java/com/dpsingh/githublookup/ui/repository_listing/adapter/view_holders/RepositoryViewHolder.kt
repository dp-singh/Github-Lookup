package com.dpsingh.githublookup.ui.repository_listing.adapter.view_holders

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dpsingh.githublookup.GlideApp
import com.dpsingh.githublookup.R
import com.dpsingh.githublookup.domain.model.Repository
import kotlinx.android.synthetic.main.item_repo.view.*

class RepositoryViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)) {

    fun bindTo(response: Repository?) {
        response?.let {
            GlideApp.with(itemView)
                    .load(response.owner.avatar_url)
                    .centerCrop()
                    .optionalCircleCrop()
                    .into(itemView.img_owner)

            itemView.tv_title.text = it.name
            itemView.tv_subtitle.text = if (it.description.isNullOrBlank()) "No Description" else it.description
            itemView.tv_watch.text = it.watchers_count.toString()
            itemView.tv_star.text = it.stargazers_count.toString()
            itemView.tv_fork.text = it.forks_count.toString()
        }
    }

}