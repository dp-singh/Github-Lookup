package com.dpsingh.githublookup.ui.repository_listing.adapter.view_holders

import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dpsingh.githublookup.R
import com.dpsingh.githublookup.utils.ViewState
import kotlinx.android.synthetic.main.item_error_view.view.*

class StateLayoutViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_error_view, parent, false)) {


    fun handleState(state: Int, retry: () -> Unit) {
        when (state) {
            ViewState.LOADING -> showProgressView(R.string.repository_progress_text)
            ViewState.ERROR -> showErrorView(R.string.repository_error_text, retry)
            ViewState.COMPLETE->showCompletedView()
            ViewState.EMPTY -> showEmptyLayout(R.string.empty_search_history)
        }
    }

    private fun showErrorView(@StringRes value: Int, retry: () -> Unit) {
        itemView.tvStatus.setText(value)
        itemView.btn_retry.setOnClickListener { retry() }

        itemView.tvStatus.visibility = View.VISIBLE
        itemView.btn_retry.visibility = View.VISIBLE
        itemView.progress_view.visibility = View.GONE
    }

    private fun showProgressView(@StringRes value: Int) {
        itemView.tvStatus.setText(value)
        itemView.tvStatus.visibility = View.GONE
        itemView.btn_retry.visibility = View.GONE
        itemView.progress_view.visibility = View.VISIBLE
    }

    fun showEmptyLayout(@StringRes value: Int) {
        itemView.tvStatus.setText(value)
        itemView.tvStatus.visibility = View.VISIBLE
        itemView.btn_retry.visibility = View.GONE
        itemView.progress_view.visibility = View.GONE
    }

    private fun showCompletedView(){
        itemView.tvStatus.visibility = View.GONE
        itemView.btn_retry.visibility = View.GONE
        itemView.progress_view.visibility = View.GONE
    }
}