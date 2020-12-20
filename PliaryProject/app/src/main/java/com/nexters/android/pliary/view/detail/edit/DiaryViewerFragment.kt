package com.nexters.android.pliary.view.detail.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nexters.android.pliary.R
import com.nexters.android.pliary.analytics.AnalyticsUtil
import com.nexters.android.pliary.analytics.FBEvents
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.databinding.FragmentDiaryViewerLayoutBinding
import com.nexters.android.pliary.view.detail.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
            val popup = PopupMenu(context, it)
            activity?.menuInflater?.inflate(R.menu.card_menu, popup.menu)
            popup.apply {
                setOnMenuItemClickListener {
                    when(it.itemId) {
                        R.id.modify -> {
                            navigate(R.id.action_diaryViewerFragment_to_diaryEditFragment,
                                Bundle().apply {
                                    putLong("diaryID", diaryID)
                                })
                            AnalyticsUtil.event(FBEvents.DIARY_DETAIL_MENU_EDIT_CLICK)
                            true
                        }
                        R.id.delete -> {
                            showDeleteDialog()
                            AnalyticsUtil.event(FBEvents.DIARY_DETAIL_MENU_DELETE_CLICK)
                            true
                        }
                        else -> false
                    }
                }
            }.show()
            AnalyticsUtil.event(FBEvents.DIARY_DETAIL_MORE_CLICK)
        }
        binding.ivBack.setOnClickListener { popBackStack() }
    }

    private fun initData() {
        viewModel.localDataSource.diary(diaryID).observe(viewLifecycleOwner, Observer {
            binding.item = it
        })

    }

    private fun initView(){
        if(diaryID > 0) { //수정

        }
    }

    private fun showDeleteDialog() {
        context?.let {
            androidx.appcompat.app.AlertDialog.Builder(it, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setMessage(getString(R.string.diary_delete_message))
                .setCancelable(false)
                .setPositiveButton(R.string.delete) { _, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.localDataSource.deleteDiary(diaryID)
                    }
                    popBackStack()
                }
                .setNegativeButton(R.string.cancel) { i, a -> i.dismiss() }
                .show()
        }
    }
}