package com.nexters.android.pliary.view.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.transition.ChangeBounds
import androidx.transition.TransitionInflater
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment  : BaseFragment<DetailViewModel>() {

    override fun getModelClass(): Class<DetailViewModel> = DetailViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        //sharedElementReturnTransition = transition
        prepareTransitions()
    }

    private fun prepareTransitions() {
        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
                background?.apply { sharedElements?.put(transitionName, this) }
                }
            }

        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    private fun initView() {

    }
}