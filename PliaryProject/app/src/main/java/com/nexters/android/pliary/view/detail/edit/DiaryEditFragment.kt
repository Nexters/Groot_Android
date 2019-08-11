package com.nexters.android.pliary.view.detail.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment

class DiaryEditFragment : BaseFragment<DiaryEditViewModel>() {

    override fun getModelClass(): Class<DiaryEditViewModel> = DiaryEditViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_diary_edit_layout, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {

    }
}