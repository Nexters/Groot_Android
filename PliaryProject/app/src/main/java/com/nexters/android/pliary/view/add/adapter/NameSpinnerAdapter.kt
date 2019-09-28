package com.nexters.android.pliary.view.add.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.nexters.android.pliary.R

class NameSpinnerAdapter(val context: Context) : BaseAdapter() {

    var engNameList = context.resources.getStringArray(R.array.array_plant)
    var krNameList = context.resources.getStringArray(R.array.array_plant_kr)


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.spinner_custom_layout, parent, false)
        val title = view.findViewById<TextView>(R.id.tvName)
        title.text = engNameList[position]
        return view
    }

    override fun getItem(position: Int): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemId(position: Int): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}