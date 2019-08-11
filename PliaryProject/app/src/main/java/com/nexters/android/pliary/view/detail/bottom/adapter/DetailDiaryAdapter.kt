package com.nexters.android.pliary.view.detail.bottom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexters.android.pliary.R
import com.nexters.android.pliary.databinding.DiaryItemBinding
import com.nexters.android.pliary.view.detail.bottom.data.DiaryData

class DetailDiaryAdapter : RecyclerView.Adapter<DetailDiaryAdapter.DiaryViewHolder>() {

    lateinit var diaryList : ArrayList<DiaryData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<DiaryItemBinding>(inflater, R.layout.diary_item, parent, false)
        return DiaryViewHolder(binding)
    }

    override fun getItemCount(): Int = diaryList.size

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.bindView(diaryList[position])
    }

    inner class DiaryViewHolder(val binding: DiaryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item : DiaryData) {
            binding.item = item
        }
    }
}