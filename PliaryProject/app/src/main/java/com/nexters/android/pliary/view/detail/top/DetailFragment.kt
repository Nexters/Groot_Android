package com.nexters.android.pliary.view.detail.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.transition.TransitionInflater
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.view.detail.DetailViewModel
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

        ivBack.setOnClickListener { popBackStack() }
        ivArrowDown.setOnClickListener { navigate(R.id.action_detailFragment_to_detailBottomFragment) }

    }

    private fun initView() {

    }
}