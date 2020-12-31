package com.nexters.android.pliary.view.add.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import com.nexters.android.pliary.R


class NameSpinnerAdapter(context: Context, val layout: Int, val engNameList: Array<String>, val krNameList: Array<String>, val selected: Int = -1) : ArrayAdapter<String>(context, layout) {
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = engNameList.count()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: inflater.inflate(layout, parent, false)
        val title = view.findViewById<TextView>(R.id.tvName)
        val titleKr = view.findViewById<TextView>(R.id.tvNameKr)
        title.text = engNameList[position]
        titleKr.text = krNameList[position]
        val check = view.findViewById<ImageView>(R.id.ivCheck)
        when {
            selected <= -1 -> check.isVisible = false
            selected == position -> check.isVisible = true
            else -> check.isVisible = false
        }

        return view
    }



}