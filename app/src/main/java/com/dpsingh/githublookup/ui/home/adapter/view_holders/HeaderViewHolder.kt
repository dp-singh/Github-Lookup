package com.dpsingh.githublookup.ui.home.adapter.view_holders

import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dpsingh.githublookup.R
import kotlinx.android.synthetic.main.item_header.view.*

class HeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)) {
    fun bind(@StringRes text: Int) {
        itemView.tv_header_content.setText(text)
    }
}