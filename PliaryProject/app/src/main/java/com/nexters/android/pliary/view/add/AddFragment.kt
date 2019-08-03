package com.nexters.android.pliary.view.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : BaseFragment<AddViewModel>() {
    override fun getModelClass(): Class<AddViewModel> = AddViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView(){
        val plantArray = resources.getStringArray(R.array.array_plant)
        spSelectPlant.adapter = ArrayAdapter<String>(context, R.layout.spinner_item, plantArray).apply{
            setDropDownViewResource(R.layout.spinner_item)
        }
    }
}