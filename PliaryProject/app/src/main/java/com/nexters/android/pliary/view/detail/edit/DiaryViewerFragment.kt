package com.nexters.android.pliary.view.detail.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.databinding.FragmentDiaryViewerLayoutBinding
import com.nexters.android.pliary.view.detail.DetailViewModel

internal class DiaryViewerFragment : BaseFragment<DiaryViewerViewModel>() {

    override fun getModelClass(): Class<DiaryViewerViewModel> = DiaryViewerViewModel::class.java
    private lateinit var binding : FragmentDiaryViewerLayoutBinding

    private val diaryID : Long by lazy { arguments?.getLong("diaryID") ?: 0L }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary_viewer_layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()

        binding.lifecycleOwner = this
        binding.vm = viewModel


        initView()

        binding.ivMenu.setOnClickListener {

        }
        binding.ivBack.setOnClickListener { popBackStack() }
    }

    private fun initData() {
        viewModel.localDataSource.diary(diaryID).observe(this, Observer {
            binding.item = it
        })

    }

    private fun initView(){
        if(diaryID > 0) { //수정

        }
    }
}