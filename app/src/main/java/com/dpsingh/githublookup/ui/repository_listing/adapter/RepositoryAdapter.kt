package com.dpsingh.githublookup.ui.repository_listing.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.dpsingh.githublookup.data.local.PagingAdapter
import com.dpsingh.githublookup.domain.model.Repository
import com.dpsingh.githublookup.ui.repository_listing.adapter.view_holders.RepositoryViewHolder


class RepositoryAdapter(retry: () -> Unit) : PagingAdapter<Repository>(DIFF_CALLBACK, retry) {

    override fun handleBindView(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RepositoryViewHolder).bindTo(getItem(position))
    }

    override fun handleOnCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepositoryViewHolder(parent)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Repository> = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldUser: Repository, newUser: Repository) = oldUser.id == newUser.id
            override fun areContentsTheSame(oldUser: Repository, newUser: Repository) = oldUser == newUser
        }
    }
}

