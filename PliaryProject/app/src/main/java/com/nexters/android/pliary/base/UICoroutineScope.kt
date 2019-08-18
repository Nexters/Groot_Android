package com.nexters.android.pliary.base

import android.util.Log
import com.nexters.android.pliary.BuildConfig.DEBUG
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class UICoroutineScope(private val dispatchers: CoroutineContext = DispatchersProvider.main) : BaseCoroutineScope {

    override val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatchers + job

    override fun releaseCoroutine() {
        if (DEBUG) {
            Log.d("UICoroutineScope", "onRelease coroutine")
        }
        job.cancel()
    }
}
