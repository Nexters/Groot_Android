package com.nexters.android.pliary.view.add.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexters.android.pliary.R
import com.nexters.android.pliary.databinding.DatePickItemBinding

class DatePickerAdapter : RecyclerView.Adapter<DatePickerAdapter.DateHolder>() {

    companion object {
        const val MIN_VALUE = 1
        const val MAX_VALUE = 60
        const val MULTIPLE_SIZE = 1
    }

    private val dateList = (MIN_VALUE..MAX_VALUE).toList().map { it.toString() }


    interface Callback {
        fun onItemClicked(view: View)
    }
    var callback: Callback? = null
    val clickListener = View.OnClickListener { v ->
        v?.let {
            callback?.onItemClicked(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<DatePickItemBinding>(inflater, R.layout.date_pick_item, parent, false)
        return DateHolder(binding)
    }

    override fun getItemCount(): Int = dateList.count()

    override fun onBindViewHolder(holder: DateHolder, position: Int) {
        holder.bindView(dateList[position])
    }

    inner class DateHolder(private val binding : DatePickItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: String) {
            binding.data = item
            binding.tvDate.setOnClickListener {
                callback?.onItemClicked(it)
                //(it as TextView).setTypeface(null, Typeface.BOLD)
            }
        }
    }
}