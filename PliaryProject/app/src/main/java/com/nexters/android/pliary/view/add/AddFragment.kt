package com.nexters.android.pliary.view.add

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.data.PlantSpecies
import com.nexters.android.pliary.data.PlantSpecies.Companion.PLANT_HANGING
import com.nexters.android.pliary.data.PlantSpecies.Companion.PLANT_USERS
import com.nexters.android.pliary.data.PlantSpecies.Companion.makePlantArray
import com.nexters.android.pliary.databinding.FragmentAddBinding
import com.nexters.android.pliary.notification.AlarmBroadcastReceiver
import com.nexters.android.pliary.view.add.adapter.DatePickerAdapter
import com.nexters.android.pliary.view.util.*
import kotlinx.android.synthetic.main.add_first_layout.*
import kotlinx.android.synthetic.main.add_second_layout.view.*
import java.text.SimpleDateFormat
import java.util.*


internal class AddFragment : BaseFragment<AddViewModel>(), DialogFactory.SelectPlantDialogListener {

    private lateinit var binding : FragmentAddBinding
    private val plantList = makePlantArray()
    private var selectPlant : PlantSpecies? = null
    private lateinit var plantArray : Array<String>
    private var selected = -1

    override fun getModelClass(): Class<AddViewModel> = AddViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setObserver()

        binding.apply{
            secondLayout.tvGetDate.setOnClickListener { showDatePickerDialog(it) }
            secondLayout.tvLastDate.setOnClickListener { showDatePickerDialog(it) }
            ivClose.setOnClickListener { popBackStack() }
        }
    }

    private fun initView(){
        context?.let{
            plantArray = it.resources.getStringArray(R.array.array_plant)
        }

        initHorizontalNumberPicker()

        binding.clContainer.setOnClickListener { activity?.hideSoftKeyboard() }
        binding.tvSelectPlant.setOnClickListener {
            context?.let{
                DialogFactory.showSelectPlantDialog(it, selected, plantArray, this)
            }
        }
    }

    override fun onSelect(position: Int) {
        selected = position
        binding.scrollView.isVisible = true
        viewModel.onSelectPlant(getPlantSpecies(position))
        binding.tvSelectPlant.text = plantArray[position]
    }

    private fun initHorizontalNumberPicker() {
        val padding: Int = context?.let{ getScreenWidth(it)/2 - it.dpToPx(40) } ?: 0

        binding.secondLayout.rvDatePicker.apply {
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
        return plantList[position]
    }

    private fun setObserver() {
        viewModel.plantSelectEvent.observe(viewLifecycleOwner, Observer {
            selectPlant = it
            binding.firstSection.clUserInput.isVisible = (it.id == PLANT_USERS || it.id == PLANT_HANGING)
            binding.apply {
                etEngName.setText(it.name)
                etKorName.setText(it.nameKr)
                clPlantImage.setGIF(it.posUrl, true)
                clInfo.isVisible = !it.info.isNullOrEmpty()
                tvRefContent.text = it.info
            }
        })

        viewModel.engName.observe(viewLifecycleOwner, Observer { selectPlant?.name = it })

        viewModel.korName.observe(viewLifecycleOwner, Observer { selectPlant?.nameKr = it })

        viewModel.enableDone.observe(viewLifecycleOwner, Observer {
            binding.tvDone.isEnabled = it
        })

        viewModel.plantDoneEvent.observe(viewLifecycleOwner, Observer {
            registAlarm(it.willbeWateringDate, it.nickName?: "", it.id.toInt())
            popBackStack()
            Toast.makeText(context, getString(com.nexters.android.pliary.R.string.add_complete), Toast.LENGTH_LONG).show()
        })
    }

    private fun showDatePickerDialog(view: View) {
        val newCalendar = Calendar.getInstance()

        DatePickerDialog(view.context, R.style.PliaryDatePickerSpinnerTheme,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate.set(year, monthOfYear, dayOfMonth)
                if(view is TextView) view.text = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(newDate.time)
            },
            newCalendar.get(Calendar.YEAR),
            newCalendar.get(Calendar.MONTH),
            newCalendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.maxDate = newCalendar.time.time
        }.show()

    }

    private fun registAlarm(alarmDate: String, nickname: String, id: Int) {

        val am = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            putExtra(AlarmBroadcastReceiver.NOTIFICATION_TITLE, getString(R.string.noti_title))
            putExtra(AlarmBroadcastReceiver.NOTIFICATION_CONTENT, getString(R.string.noti_message, nickname))
            putExtra(AlarmBroadcastReceiver.NOTIFICATION_ID, id)
        }

        val sender = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val calendar = Calendar.getInstance()
        //알람시간 calendar에 set해주기
        val dateFormat = SimpleDateFormat("yyyy.MM.dd'T'HH:mm:ss.SSSX", Locale.KOREA)
        calendar.time = dateFormat.parse("${alarmDate}T08:00:35.741+09")


        //알람 예약

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, sender)
        } else {
            am.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, sender)
        }
    }

}