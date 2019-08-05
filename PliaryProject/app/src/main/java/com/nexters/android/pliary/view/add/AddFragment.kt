package com.nexters.android.pliary.view.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.core.view.isVisible
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import kotlinx.android.synthetic.main.add_second_layout.*
import kotlinx.android.synthetic.main.fragment_add.*
import java.text.SimpleDateFormat
import java.util.*


class AddFragment : BaseFragment<AddViewModel>() {

    //TODO : AndroidThreeTen 적용하기
    val dateFormatter = SimpleDateFormat("yyyy.MM.dd", Locale.US)

    override fun getModelClass(): Class<AddViewModel> = AddViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        etGetDate.setOnClickListener { showDatePickerDialog(etGetDate) }
        ivClose.setOnClickListener { popBackStack() }
    }

    private fun initView(){
        val plantArray = resources.getStringArray(com.nexters.android.pliary.R.array.array_plant)
        spSelectPlant.apply {
            adapter = ArrayAdapter<String>(context, com.nexters.android.pliary.R.layout.spinner_item, plantArray).apply {
                setDropDownViewResource(com.nexters.android.pliary.R.layout.spinner_item)
            }
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    scrollView.isVisible = position != 0
                }
            }
        }
    }

    private fun showDatePickerDialog(view: View) {
        val newCalendar = Calendar.getInstance()

        DatePickerDialog(context,
            R.style.PliaryDatePickerSpinnerTheme,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate.set(year, monthOfYear, dayOfMonth)
                if(view is EditText) view.setText(dateFormatter.format(newDate.time))
            },
            newCalendar.get(Calendar.YEAR),
            newCalendar.get(Calendar.MONTH),
            newCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()

    }
}