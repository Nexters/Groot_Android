package com.nexters.android.pliary.view.detail.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.databinding.FragmentDetailBinding
import com.nexters.android.pliary.view.detail.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

internal class DetailFragment  : BaseFragment<DetailViewModel>() {

    override fun getModelClass(): Class<DetailViewModel> = DetailViewModel::class.java

    private val careID : Long by lazy { arguments?.getLong("cardID") ?: 0L }
    private lateinit var binding : FragmentDetailBinding

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
                ivBackGround?.apply { sharedElements?.put(transitionName, this) }
                ibtnWater?.apply { sharedElements?.put(transitionName, this) }
                clDday?.apply { sharedElements?.put(transitionName, this) }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //val view = inflater.inflate(R.layout.fragment_detail, container, false)
        binding = DataBindingUtil.inflate<FragmentDetailBinding>(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLivePlantData(careID)
        initObserver()
        initView()

        ivBack.setOnClickListener { popBackStack() }
        ivArrowDown.setOnClickListener { navigate(R.id.action_detailFragment_to_detailBottomFragment) }

    }

    private fun initObserver(){
        viewModel.liveData.observe(this, Observer {
            binding.item = it
        })
    }
    private fun initView() {

        //setGIF()
    }

    private fun setGIF() {
        context?.apply{
            Glide.with(this)
                .asGif()
                .load(R.raw.pliary_gif_test)
                .centerCrop()
                .placeholder(R.drawable.plant_placeholder)
                .into(ivBackGround)
        }
    }
}