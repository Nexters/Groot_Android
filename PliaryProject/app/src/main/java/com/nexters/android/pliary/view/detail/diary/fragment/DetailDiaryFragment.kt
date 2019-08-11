package com.nexters.android.pliary.view.detail.diary.fragment

import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_diary_layout.*
import javax.inject.Inject

class DetailDiaryFragment : BaseFragment<DetailDiaryViewModel>() {

    @Inject
    lateinit var diaryAdapter : DetailDiaryAdapter

    override fun getModelClass(): Class<DetailDiaryViewModel> = DetailDiaryViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_diary_layout, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        diaryAdapter.setDiaryList(arrayListOf(
            DiaryData.DiaryItem(
            id = 0,
            writeDate = "2019.08.11",
            imageUrl = "https://avatars1.githubusercontent.com/u/7722921?s=460&v=4",
            content = "이것은 테스트입니당당당ㄷ앋앋ㅇ"
        ), DiaryData.DiaryItem(
            id = 0,
            writeDate = "2019.08.11",
            imageUrl = "https://avatars1.githubusercontent.com/u/7722921?s=460&v=4",
            content = "이것은 테스트입니당당당ㄷ앋앋ㅇ"
        ), DiaryData.DiaryItem(
            id = 0,
            writeDate = "2019.08.11",
            imageUrl = "https://avatars1.githubusercontent.com/u/7722921?s=460&v=4",
            content = "이것은 테스트입니당당당ㄷ앋앋ㅇ"
        ))
        )

        rvDiary.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = diaryAdapter
            setHasFixedSize(true)
            addItemDecoration(CardItemDecoration(15))
        }

        val isListEmpty = diaryAdapter.getDiaryList().isEmpty()
        tvEmpty.isVisible = isListEmpty
        rvDiary.isVisible = !isListEmpty

    }
}