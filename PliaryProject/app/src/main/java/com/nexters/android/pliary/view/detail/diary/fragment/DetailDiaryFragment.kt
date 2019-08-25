package com.nexters.android.pliary.view.detail.diary.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.view.detail.diary.data.DiaryData
import com.nexters.android.pliary.view.detail.diary.viewmodel.DetailDiaryViewModel
import com.nexters.android.pliary.view.detail.diary.adapter.DetailDiaryAdapter
import com.nexters.android.pliary.view.util.CardItemDecoration
import androidx.lifecycle.Observer
import com.nexters.android.pliary.databinding.FragmentDiaryLayoutBinding
import com.nexters.android.pliary.db.entity.Plant
import com.nexters.android.pliary.view.detail.DetailViewModel
import com.nexters.android.pliary.view.detail.bottom.fragment.DetailBottomFragment
import com.nexters.android.pliary.view.util.eventObserver
import kotlinx.android.synthetic.main.fragment_diary_layout.*
import kotlinx.coroutines.*
import javax.inject.Inject

internal class DetailDiaryFragment : BaseFragment<DetailDiaryViewModel>() {

    private val TAG = this.toString()

    @Inject
    lateinit var diaryAdapter : DetailDiaryAdapter
    lateinit var detailVM : DetailViewModel
    lateinit var binding : FragmentDiaryLayoutBinding

    private var cardID : Long = -1
    val diaryList = arrayListOf<DiaryData>()

    override fun getModelClass(): Class<DetailDiaryViewModel> = DetailDiaryViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val parent = parentFragment?.parentFragment?.parentFragment
        detailVM = createSharedViewModel(parent!!, DetailViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary_layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardID = detailVM.cardLiveID

        getData()
        //initView()
    }

    private fun getData() {
        detailVM.plantLiveData.observe(this, Observer {
            viewModel.reqDateCount(it)
        })

        viewModel.localDataSource.diaries(cardID).observe(this, Observer {
            viewModel.reqDiaryList(it)
        })

        viewModel.diaryList.observe(this, eventObserver {
            diaryList.addAll(it)
            diaryAdapter.submitList(it)
            //diaryAdapter.notifyDataSetChanged()
            initView()
        })
    }

    private fun initView() {
        binding.rvDiary.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = diaryAdapter
            setHasFixedSize(true)
            addItemDecoration(CardItemDecoration(15))
        }

        binding.tvEmpty.isVisible = diaryAdapter.itemCount <= 1

    }
}