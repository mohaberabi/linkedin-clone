package com.mohaberabi.presentation.ui.util.extension

import android.view.View
import androidx.viewbinding.ViewBinding


fun View.hide() {
    visibility = View.GONE
}

fun View.hideAndKeep() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.setVisible(
    visible: Boolean
) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun ViewBinding.showAll(
    vararg views: View
) {
    views.forEach {
        it.show()
    }
}

fun ViewBinding.hideAll(
    vararg views: View
) {
    views.forEach {
        it.hide()
    }
}

inline fun <reified T : View> T.showIf(
    predicate: Boolean,
    keepOnTree: Boolean = false,
    action: T.() -> Unit = {},
): T {
    if (!predicate) {
        if (keepOnTree) {
            hideAndKeep()
        } else {
            hide()
        }
    } else {
        show()
        this.apply(action)
    }
    return this
}

