package com.mohaberabi.presentation.ui.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class AppRecyclerViewScrollListener(
    private val isLinear: Boolean,
    private val threshold: Int = 1,
    private val onPaginate: () -> Unit,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int
    ) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager.let {
            if (isLinear) it as LinearLayoutManager else it as GridLayoutManager
        }
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
        val tendToPage = dy > 0
        val needsToGetMoreItems = lastVisibleItem + threshold >= totalItemCount
        if (tendToPage && needsToGetMoreItems) {
            onPaginate()
        }
    }
}