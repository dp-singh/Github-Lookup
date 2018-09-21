package com.dpsingh.githublookup.data.paging

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.dpsingh.githublookup.ui.repository_listing.adapter.view_holders.StateLayoutViewHolder
import com.dpsingh.githublookup.utils.ViewState

abstract class PagingAdapter<T>(DIFF_CALLBACK: DiffUtil.ItemCallback<T>, private var retry: () -> Unit) : PagedListAdapter<T, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private var state: Int = ViewState.LOADING

    fun setPagingState(pagingState: PagingState<T>) {

        //Paging Lib through empty data at start and you need to pass it to sublist to get it working
        if (pagingState.state == ViewState.SUCCESS && pagingState.data?.isEmpty() == true) {
            submitList(pagingState.data)
            return
        }

        //if it already showing any state remove it
        if (hasExtra()) {
            state = ViewState.UNKNOWN
            notifyItemRemoved(itemCount)
        }

        //Insert data at the corresponding state
        this.state = pagingState.state
        when (pagingState.state) {
            ViewState.LOADING -> notifyItemInserted(itemCount + 1)
            ViewState.ERROR -> notifyItemInserted(itemCount + 1)
            ViewState.SUCCESS -> submitList(pagingState.data)
            ViewState.EMPTY->notifyItemInserted(itemCount + 1)
            ViewState.COMPLETE->{
                //don't do anything as item has already been removed
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position + 1 == itemCount && hasExtra())
            state
        else
            ViewState.SUCCESS
    }

    private fun hasExtra(): Boolean {
        return ViewState.ERROR == state || ViewState.LOADING == state || ViewState.EMPTY == state
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtra()) 1 else 0
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StateLayoutViewHolder -> holder.handleState(state, retry)
            else -> handleBindView(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewState.SUCCESS -> handleOnCreateViewHolder(parent, viewType)
            else -> StateLayoutViewHolder(parent)
        }
    }


    abstract fun handleBindView(holder: RecyclerView.ViewHolder, position: Int)

    abstract fun handleOnCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
}
