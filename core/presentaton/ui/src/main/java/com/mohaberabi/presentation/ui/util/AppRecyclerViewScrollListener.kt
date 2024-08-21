package com.mohaberabi.presentation.ui.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class AppRecyclerViewScrollListener(
    private val isLinear: Boolean,
    private val onPaginate: (lastVisible: Int, total: Int) -> Unit,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager.let {
            if (isLinear) it as LinearLayoutManager else it as GridLayoutManager
        }
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
        onPaginate(lastVisibleItem, totalItemCount)
    }
}