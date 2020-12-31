package com.nexters.android.pliary.view.detail.diary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nexters.android.pliary.R
import com.nexters.android.pliary.databinding.DiaryDatecountItemBinding
import com.nexters.android.pliary.databinding.DiaryItemBinding
import com.nexters.android.pliary.view.detail.diary.data.DiaryData
import com.nexters.android.pliary.view.detail.diary.data.DiaryData.Companion.DIARY_DATE
import com.nexters.android.pliary.view.detail.diary.data.DiaryData.Companion.DIARY_ITEM

class DetailDiaryAdapter(
    private val onClickDiaryCard: ((Long) -> Unit)?,
    private val onClickMenu: ((View, Long)->Unit)?
) : ListAdapter<DiaryData, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<DiaryData>() {
    override fun areItemsTheSame(oldItem: DiaryData, newItem: DiaryData): Boolean {
        return oldItem.listItemId == newItem.listItemId
    }

    override fun areContentsTheSame(oldItem: DiaryData, newItem: DiaryData): Boolean {
        return oldItem.listItemId == newItem.listItemId
    }

}){

    override fun getItemViewType(position: Int): Int = currentList[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            DIARY_DATE -> {
                val binding = DataBindingUtil.inflate<DiaryDatecountItemBinding>(inflater, R.layout.diary_datecount_item, parent, false)
                DateCountViewHolder(binding)
            }
            DIARY_ITEM -> {
                val binding = DataBindingUtil.inflate<DiaryItemBinding>(inflater, R.layout.diary_item, parent, false)
                DiaryViewHolder(binding, onClickDiaryCard, onClickMenu)
            }
            else -> {
                val binding = DataBindingUtil.inflate<DiaryItemBinding>(inflater, R.layout.diary_item, parent, false)
                DiaryViewHolder(binding, onClickDiaryCard, onClickMenu)
            }
        }

    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is DiaryViewHolder -> holder.bindView(currentList[position] as DiaryData.DiaryItem)
            is DateCountViewHolder -> holder.bindView(currentList[position] as DiaryData.DateCount)
        }
    }

}

internal class DiaryViewHolder(val binding: DiaryItemBinding,
                               private val onClickDiaryCard: ((Long) -> Unit)?,
                               private val onClickMenu: ((View, Long)->Unit)?) : RecyclerView.ViewHolder(binding.root) {
    fun bindView(item : DiaryData.DiaryItem) {
        binding.item = item

        binding.cvDiary.setOnClickListener { onClickDiaryCard?.invoke(item.id.toLong()) }
        binding.ivMenu.setOnClickListener { onClickMenu?.invoke(it, item.id.toLong()) }
    }
}

internal class DateCountViewHolder(val binding: DiaryDatecountItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindView(item : DiaryData.DateCount) {
        binding.item = item
    }
}