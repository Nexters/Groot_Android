package com.nexters.android.pliary.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import com.nexters.android.pliary.R
import com.nexters.android.pliary.view.util.hideSoftKeyboard
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
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

    protected fun <VM : ViewModel> createSharedViewModel(fragment: Fragment, viewModelClass: Class<VM>) : VM {
        return ViewModelProviders.of(fragment, viewModelProviderFactory).get(viewModelClass)
    }

    protected fun navigate(@IdRes id: Int, bundle: Bundle? = null) {
        findNavController().navigate(
            id,
            bundle
        )
        activity?.hideSoftKeyboard()
    }

    protected fun navigate(@IdRes id: Int, args : Bundle?, navOptions : NavOptions?, extras : Navigator.Extras?) {
        findNavController().navigate(id,
            args, // Bundle of args
            navOptions, // NavOptions
            extras)

        activity?.hideSoftKeyboard()
    }

    protected fun popBackStack(unit: Unit) {
        findNavController().popBackStack()
        activity?.hideSoftKeyboard()
    }

    protected fun popBackStack() {
        findNavController().popBackStack()
        activity?.hideSoftKeyboard()
    }

    protected fun popBackStack(@IdRes destinationId: Int, inclusive: Boolean = true) {
        findNavController().popBackStack(destinationId, inclusive)
        activity?.hideSoftKeyboard()
    }

    protected fun getNavOptions(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.fade_out)
            .build()
    }
}