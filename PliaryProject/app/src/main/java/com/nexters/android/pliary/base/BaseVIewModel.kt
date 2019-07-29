package com.nexters.android.pliary.base


import android.content.Intent
import android.os.Bundle
import androidx.annotation.CheckResult
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCleared() {
        compositeDisposable.clear()

        super.onCleared()
    }

    fun bind(vararg disposables: Disposable) {
        compositeDisposable.addAll(*disposables)
    }

}