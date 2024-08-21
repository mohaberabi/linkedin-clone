package com.mohaberabi.presentation.ui.util

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class AppDiffCallBack<T>(
    private val predicate: (T) -> String,
) : DiffUtil.ItemCallback<T>() {
    override fun areContentsTheSame(
        oldItem: T & Any,
        newItem: T & Any
    ): Boolean = predicate(oldItem) == predicate(newItem)

    override fun areItemsTheSame(
        oldItem: T & Any,
        newItem: T & Any,
    ): Boolean = oldItem == newItem
}

abstract class AppListAdapter<T, VH : ViewHolder>(
    predicate: (T) -> String,
) : ListAdapter<T, VH>(AppDiffCallBack<T>(predicate))
