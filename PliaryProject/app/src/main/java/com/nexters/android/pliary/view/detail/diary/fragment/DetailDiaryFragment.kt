package com.nexters.android.pliary.view.detail.diary.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.view.detail.diary.data.DiaryData
import com.nexters.android.pliary.view.detail.diary.viewmodel.DetailDiaryViewModel
import com.nexters.android.pliary.view.detail.diary.adapter.DetailDiaryAdapter
import com.nexters.android.pliary.view.util.CardItemDecoration
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nexters.android.pliary.databinding.FragmentDiaryLayoutBinding
import com.nexters.android.pliary.db.entity.Plant
import com.nexters.android.pliary.view.detail.DetailViewModel
import com.nexters.android.pliary.view.detail.bottom.fragment.DetailBottomFragment
import com.nexters.android.pliary.view.main.MainViewModel
import com.nexters.android.pliary.view.util.eventObserver
import kotlinx.android.synthetic.main.fragment_diary_layout.*
import kotlinx.coroutines.*
import javax.inject.Inject

internal class DetailDiaryFragment : BaseFragment<DetailDiaryViewModel>() {

    private val TAG = this.toString()

    @Inject
    lateinit var diaryAdapter : DetailDiaryAdapter
    lateinit var mainVM : MainViewModel
    lateinit var binding : FragmentDiaryLayoutBinding

    private var cardID : Long = -1
    private val diaryList = arrayListOf<DiaryData>()

    override fun getModelClass(): Class<DetailDiaryViewModel> = DetailDiaryViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /*return if(::binding.isInitialized) {
            binding.root
        } else {
            mainVM = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary_layout, container, false)
            with(binding) {
                root
            }
        }*/
        mainVM = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary_layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardID = mainVM.cardLiveID
        diaryList.clear()
        diaryList.add(DiaryData.DateCount(
            dateCount = 100, //plant.takeDate,
            nickName = mainVM.plantLiveData.nickName
        ))

        initView()
        getData()
    }

    private fun getData() {
        viewModel.localDataSource.diaries(cardID).observe(this, Observer {
            viewModel.reqDiaryList(it)
        })

        viewModel.diaryList.observe(this, eventObserver {
            diaryList.addAll(it)
            diaryAdapter.submitList(diaryList)
            binding.tvEmpty.isVisible = diaryList.count() <= 1
        })

    }

    private fun initView() {
        binding.rvDiary.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = diaryAdapter
            setHasFixedSize(true)
            addItemDecoration(CardItemDecoration(15))
        }
    }
}