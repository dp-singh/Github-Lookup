package com.dpsingh.githublookup.data.local

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.dpsingh.githublookup.R
import com.dpsingh.githublookup.ui.repository_listing.adapter.view_holders.StateLayoutViewHolder
import com.dpsingh.githublookup.utils.ViewState

abstract class PagingViewHolder<T>(DIFF_CALLBACK: DiffUtil.ItemCallback<T>, private var retry: () -> Unit) : PagedListAdapter<T, RecyclerView.ViewHolder>(DIFF_CALLBACK) {


    private var state: Int = ViewState.LOADING

    fun setPagingState(pagingState: PagingState<T>) {
        val extraShowing = hasExtra()
        //check before setting state is extra column is showing
        this.state = pagingState.state
        when (pagingState.state) {
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
                submitList(pagingState.data)
            }
            ViewState.COMPLETE -> {
                if (extraShowing) {
                    notifyItemRemoved(itemCount + 1)
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int = if (hasExtra() && position == super.getItemCount()) state else ViewState.SUCCESS

    private fun hasExtra(): Boolean = ViewState.ERROR == state || ViewState.LOADING == state

    override fun getItemCount(): Int = super.getItemCount() + if (hasExtra()) 1 else 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StateLayoutViewHolder -> when (state) {
                ViewState.LOADING -> holder.showProgressView(R.string.repository_progress_text)
                ViewState.ERROR -> holder.showErrorView(R.string.repository_error_text, retry)
            }
            else -> handleBindView(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewState.ERROR -> StateLayoutViewHolder(parent)
            ViewState.LOADING -> StateLayoutViewHolder(parent)
            else -> handleOnCreateViewHolder(parent, viewType)
        }
    }

    abstract fun handleBindView(holder: RecyclerView.ViewHolder, position: Int)

    abstract fun handleOnCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
}
