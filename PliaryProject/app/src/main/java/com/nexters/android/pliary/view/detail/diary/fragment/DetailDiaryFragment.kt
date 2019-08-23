package com.nexters.android.pliary.view.detail.diary.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.view.detail.diary.data.DiaryData
import com.nexters.android.pliary.view.detail.diary.viewmodel.DetailDiaryViewModel
import com.nexters.android.pliary.view.detail.diary.adapter.DetailDiaryAdapter
import com.nexters.android.pliary.view.util.CardItemDecoration
import androidx.lifecycle.Observer
import com.nexters.android.pliary.db.entity.Plant
import com.nexters.android.pliary.view.detail.DetailViewModel
import com.nexters.android.pliary.view.util.eventObserver
import kotlinx.android.synthetic.main.fragment_diary_layout.*
import javax.inject.Inject

internal class DetailDiaryFragment : BaseFragment<DetailDiaryViewModel>() {

    private val TAG = this::getTag.toString()

    @Inject
    lateinit var diaryAdapter : DetailDiaryAdapter
    @Inject
    lateinit var detailVM : DetailViewModel

    private var cardID : Long = 0
    private lateinit var plantData : Plant

    override fun getModelClass(): Class<DetailDiaryViewModel> = DetailDiaryViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_diary_layout, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardID = detailVM.cardLiveID.value ?: 0
        //detailVM.plantLiveData.observe(this, Observer { plantData = it })

        getData()
        initView()
    }

    private fun getData() {
        viewModel.localDataSource.diaries(cardID).observe(this, Observer {
            viewModel.reqDiaryList(detailVM.plantLiveData.value, it)
        })

        viewModel.diaryList.observe(this, eventObserver {
            diaryAdapter.setDiaryList(it)
        })
    }
    private fun initView() {


        rvDiary.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = diaryAdapter
            setHasFixedSize(true)
            addItemDecoration(CardItemDecoration(15))
        }

        val isListEmpty = diaryAdapter.getDiaryList().count() <= 1
        tvEmpty.isVisible = isListEmpty

    }
}