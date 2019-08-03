package com.nexters.android.pliary.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel> : DaggerFragment() {



    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    protected val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        createViewModel(getModelClass())
    }

    protected abstract fun getModelClass(): Class<VM>

    protected fun <VM : ViewModel> createViewModel(viewModelClass: Class<VM>): VM {
        return ViewModelProviders.of(this, viewModelProviderFactory).get(viewModelClass)
    }


}