package com.mohaberabi.presentation.ui.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder


fun RecyclerView.submitOnce(
    newLayoutManager: LayoutManager,
    listAdapter: ListAdapter<*, *>,
    scrollListener: RecyclerView.OnScrollListener? = null
) {
    if (layoutManager == null) {
        layoutManager = newLayoutManager
        adapter = listAdapter
        scrollListener?.let {
            addOnScrollListener(it)
        }
    }
}

fun <T, VH : ViewHolder> ListAdapter<T, VH>.submitIfDifferent(
    data: List<T>,
) {
    if (currentList != data) {
        submitList(data)
    }
}