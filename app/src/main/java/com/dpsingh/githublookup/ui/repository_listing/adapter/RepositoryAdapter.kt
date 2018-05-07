package com.dpsingh.githublookup.ui.repository_listing.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.dpsingh.githublookup.R
import com.dpsingh.githublookup.domain.model.Repository
import com.dpsingh.githublookup.ui.repository_listing.adapter.view_holders.RepositoryViewHolder
import com.dpsingh.githublookup.ui.repository_listing.adapter.view_holders.StateLayoutViewHolder
import com.dpsingh.githublookup.utils.ViewState


class RepositoryAdapter(var retry: () -> Unit) : PagedListAdapter<Repository, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Repository> = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldUser: Repository, newUser: Repository) = oldUser.id == newUser.id
            override fun areContentsTheSame(oldUser: Repository, newUser: Repository) = oldUser == newUser
        }
    }

    private var state: Int = ViewState.LOADING

    fun setStateValue(state: Int) {

        //do not update adapter again
        if (state == this.state) return

        //check before setting state is extra column is showing
        val extraShowing = hasExtra()
        this.state = state


        when (this.state) {
            ViewState.LOADING -> {
                if (extraShowing)
                    notifyItemChanged(itemCount)
                else
                    notifyItemInserted(super.getItemCount())
            }
            ViewState.ERROR -> {
                if (extraShowing)
                    notifyItemChanged(itemCount)
                else
                    notifyItemInserted(super.getItemCount())
            }
            ViewState.SUCCESS -> {
                if (extraShowing) {
                    notifyItemRemoved(itemCount + 1)
                }
            }
            ViewState.COMPLETE -> {
                if (extraShowing) {
                    notifyItemRemoved(itemCount + 1)
                }
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewState.ERROR -> StateLayoutViewHolder(parent)
            ViewState.LOADING -> StateLayoutViewHolder(parent)
            else -> RepositoryViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RepositoryViewHolder -> holder.bindTo(getItem(position))
            is StateLayoutViewHolder -> when (state) {
                ViewState.LOADING -> holder.showProgressView(R.string.repository_progress_text)
                ViewState.ERROR -> holder.showErrorView(R.string.repository_error_text, retry)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = if (hasExtra() && position == super.getItemCount()) state else ViewState.SUCCESS


    private fun hasExtra(): Boolean = ViewState.ERROR == state || ViewState.LOADING == state


    override fun getItemCount(): Int = super.getItemCount() + if (hasExtra()) 1 else 0


}

