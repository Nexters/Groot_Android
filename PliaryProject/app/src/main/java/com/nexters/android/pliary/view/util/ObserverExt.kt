package com.nexters.android.pliary.view.util

import androidx.lifecycle.Observer
import com.nexters.android.pliary.base.Event

fun <T> eventObserver(callback: (T) -> Unit): Observer<Event<T>> {
    return Observer {
        it?.getContentIfNotHandled()?.let {
            callback.invoke(it)
        }
    }
}