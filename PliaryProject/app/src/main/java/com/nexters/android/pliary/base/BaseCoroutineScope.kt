package com.nexters.android.pliary.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface BaseCoroutineScope : CoroutineScope {

    val job: Job

    /**
     * Coroutine job cancel
     */
    fun releaseCoroutine()
}