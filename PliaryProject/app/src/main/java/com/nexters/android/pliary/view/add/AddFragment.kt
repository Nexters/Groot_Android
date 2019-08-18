package com.nexters.android.pliary.view.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.data.PlantSpecies
import com.nexters.android.pliary.data.makePlantArray
import com.nexters.android.pliary.databinding.FragmentAddBinding
import com.nexters.android.pliary.view.add.adapter.DatePickerAdapter
import com.nexters.android.pliary.view.util.*
import kotlinx.android.synthetic.main.add_first_layout.*
import kotlinx.android.synthetic.main.add_second_layout.*
import kotlinx.android.synthetic.main.fragment_add.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter


internal class AddFragment : BaseFragment<AddViewModel>() {

    //TODO : AndroidThreeTen 적용하기
    val dateFormatter = SimpleDateFormat("yyyy.MM.dd", Locale.US)

    private val plantList = makePlantArray()

    override fun getModelClass(): Class<AddViewModel> = AddViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //val view = inflater.inflate(R.layout.fragment_add, container, false)
        val binding = DataBindingUtil.inflate<FragmentAddBinding>(inflater, R.layout.fragment_add, container, false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setObserver()

        tvGetDate.setOnClickListener { showDatePickerDialog(it) }
        tvLastDate.setOnClickListener { showDatePickerDialog(it) }
        ivClose.setOnClickListener { popBackStack() }
    }

    private fun initView(){
        initSpinner()
        initHorizontalNumberPicker()


    }

    private fun initSpinner() {
        val plantArray = resources.getStringArray(R.array.array_plant)
        spSelectPlant.apply {
            adapter = ArrayAdapter<String>(context, R.layout.spinner_item, R.id.tvName, plantArray).apply {
                setDropDownViewResource(R.layout.spinner_item)
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
                    viewModel.onSelectPlant(getPlantSpecies(position))

                }
            }
        }
    }

    private fun initHorizontalNumberPicker() {
        val padding: Int = context?.let{ getScreenWidth(it)/2 - it.dpToPx(40) } ?: 0

        rvDatePicker.apply {
            layoutManager = SliderLayoutManager(context).apply {
                callback = object : SliderLayoutManager.OnItemSelectedListener {
                    override fun onItemSelected(layoutPosition: Int) {
                        //tvSelectedItem.setText(data[layoutPosition])
                        viewModel.waterTerm.value = layoutPosition.toString()
                    }
                }
            }
            adapter = DatePickerAdapter().apply {
                callback = object : DatePickerAdapter.Callback {
                    override fun onItemClicked(view: View) {
                        rvDatePicker.smoothScrollToPosition(rvDatePicker.getChildLayoutPosition(view))
                    }
                }
            }
            setPadding(padding, 0, padding, 0)
        }
    }

    private fun getPlantSpecies(position: Int) : PlantSpecies {
        return plantList.first { it.id == position }
    }

    private fun setObserver() {
        viewModel.plantSelectEvent.observe(this, Observer {
            clPlantImage.setGIF(it.posUrl)
            clInfo.isVisible = !it.info.isNullOrEmpty()
            tvRefContent.text = it.info
        })

        viewModel.enableDone.observe(this, Observer { tvDone.isEnabled = it })

        viewModel.plantDoneEvent.observe(this, Observer { popBackStack() })
    }

    private fun showDatePickerDialog(view: View) {
        val newCalendar = Calendar.getInstance()

        DatePickerDialog(view.context,
            R.style.PliaryDatePickerSpinnerTheme,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate.set(year, monthOfYear, dayOfMonth)
                if(view is TextView) view.text = dateFormatter.format(newDate.time)
            },
            newCalendar.get(Calendar.YEAR),
            newCalendar.get(Calendar.MONTH),
            newCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()

    }
}