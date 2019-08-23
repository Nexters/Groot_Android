package com.nexters.android.pliary.view.detail.diary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexters.android.pliary.R
import com.nexters.android.pliary.databinding.DiaryDatecountItemBinding
import com.nexters.android.pliary.databinding.DiaryItemBinding
import com.nexters.android.pliary.view.detail.diary.data.DiaryData

class DetailDiaryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val DIARY_DATE_COUNT = 0
        const val DIARY_ITEM = 1
    }

    private var diaryList : ArrayList<DiaryData> = arrayListOf()

    fun setDiaryList(list: ArrayList<DiaryData>) {
        if(diaryList.isNotEmpty()) {
            diaryList.clear()
        }
        diaryList.addAll(list)
    }

    fun getDiaryList() : ArrayList<DiaryData> = diaryList

    override fun getItemViewType(position: Int): Int = when (diaryList[position]) {
        is DiaryData.DateCount -> DIARY_DATE_COUNT
        is DiaryData.DiaryItem -> DIARY_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when(viewType) {
            DIARY_DATE_COUNT -> {
                val binding = DataBindingUtil.inflate<DiaryDatecountItemBinding>(inflater, R.layout.diary_datecount_item, parent, false)
                return DateCountViewHolder(binding)
            }
            DIARY_ITEM -> {
                val binding = DataBindingUtil.inflate<DiaryItemBinding>(inflater, R.layout.diary_item, parent, false)
                return DiaryViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<DiaryItemBinding>(inflater, R.layout.diary_item, parent, false)
                return DiaryViewHolder(binding)
            }
        }

    }

    override fun getItemCount(): Int = diaryList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is DiaryViewHolder -> holder.bindView(diaryList[position] as DiaryData.DiaryItem)
            is DateCountViewHolder -> holder.bindView(diaryList[position] as DiaryData.DateCount)
        }
    }

    inner class DiaryViewHolder(val binding: DiaryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item : DiaryData.DiaryItem) {
            binding.item = item
        }
    }

    inner class DateCountViewHolder(val binding: DiaryDatecountItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item : DiaryData.DateCount) {
            binding.item = item
        }
    }
}