package com.nexters.android.pliary.view.detail.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nexters.android.pliary.R
import com.nexters.android.pliary.analytics.AnalyticsUtil
import com.nexters.android.pliary.analytics.FBEvents
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.databinding.FragmentModifyBinding
import com.nexters.android.pliary.db.entity.Plant
import com.nexters.android.pliary.view.add.adapter.DatePickerAdapter
import com.nexters.android.pliary.view.main.MainViewModel
import com.nexters.android.pliary.view.util.SliderLayoutManager
import com.nexters.android.pliary.view.util.dpToPx
import com.nexters.android.pliary.view.util.getScreenWidth
import kotlinx.android.synthetic.main.add_second_layout.view.*

internal class ModifyFragment : BaseFragment<ModifyViewModel>() {
    private val TAG = this.tag.toString()

    override fun getModelClass(): Class<ModifyViewModel> = ModifyViewModel::class.java

    private lateinit var mainVM : MainViewModel
    private lateinit var binding : FragmentModifyBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if(::binding.isInitialized) {
            binding.root
        } else {
            mainVM = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_modify, container, false)
            binding.apply{
                vm = viewModel
            }
            with(binding) {
                root
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initView() {
        viewModel.plantData = mainVM.plantLiveData
        viewModel.nickname.value = viewModel.plantData.nickName
        initHorizontalNumberPicker()
        binding.apply {
            ivClose.setOnClickListener { popBackStack() }
            etNickname.setOnClickListener { AnalyticsUtil.event(FBEvents.EDIT_PLANT_NICKNAME_CLICK) }
        }

    }

    private fun initHorizontalNumberPicker() {
        val padding: Int = context?.let{ getScreenWidth(it) /2 - it.dpToPx(40) } ?: 0

        binding.rvDatePicker.apply {
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

            viewModel.plantData.waterTerm?.let{
                rvDatePicker.scrollToPosition(it)
            }
        }
    }

    private fun initObserver() {
        viewModel.enableDone.observe(viewLifecycleOwner, Observer { binding.tvDone.isEnabled = it})
        viewModel.plantDoneEvent.observe(viewLifecycleOwner, Observer {
            popBackStack()
            Toast.makeText(context, getString(R.string.modify_complete), Toast.LENGTH_LONG).show()
        })
    }
}