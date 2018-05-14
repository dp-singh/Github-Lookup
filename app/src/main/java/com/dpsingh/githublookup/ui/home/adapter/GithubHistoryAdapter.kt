package com.dpsingh.githublookup.ui.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.dpsingh.githublookup.R
import com.dpsingh.githublookup.domain.model.User
import com.dpsingh.githublookup.ui.home.adapter.view_holders.HeaderViewHolder
import com.dpsingh.githublookup.ui.home.adapter.view_holders.HistoryViewHolder
import com.dpsingh.githublookup.ui.repository_listing.adapter.view_holders.StateLayoutViewHolder
import com.dpsingh.githublookup.utils.ViewType

class GithubHistoryAdapter(val listener: (search: User,historyViewHolder: HistoryViewHolder) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var users = mutableListOf<User>()
        set(value) {
            users.clear()
            users.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.HEADER -> HeaderViewHolder(parent)
            ViewType.EMPTY_CONTENT -> StateLayoutViewHolder(parent)
            else -> HistoryViewHolder(parent)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> return if (users.size == 0) ViewType.EMPTY_CONTENT else ViewType.HEADER
            else -> ViewType.CONTENT
        }
    }


    override fun getItemCount(): Int = users.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(R.string.et_github_prev_search)
            is HistoryViewHolder -> holder.bind(users[position - 1], listener)
            is StateLayoutViewHolder -> holder.showEmptyLayout(R.string.empty_search_history)
        }
    }

}