package com.mohaberabi.presentation.ui.util

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class AppDiffCallBack<T : Any>(
    private val predicate: (T) -> String,
    private val contentPredicate: (T, T) -> Boolean,
) : DiffUtil.ItemCallback<T>() {
    override fun areContentsTheSame(
        oldItem: T,
        newItem: T,
    ): Boolean = contentPredicate(oldItem, newItem)

    override fun areItemsTheSame(
        oldItem: T,
        newItem: T,
    ): Boolean {
        val oldPredicate = predicate(oldItem)
        val newPredicate = predicate(newItem)
        return oldPredicate == newPredicate
    }

}

abstract class AppListAdapter<T : Any, VH : ViewHolder>(
    predicate: (T) -> String,
    contentPredicate: (T, T) -> Boolean,
) : ListAdapter<T, VH>(
    AppDiffCallBack<T>(
        predicate = predicate,
        contentPredicate = contentPredicate
    )
)


