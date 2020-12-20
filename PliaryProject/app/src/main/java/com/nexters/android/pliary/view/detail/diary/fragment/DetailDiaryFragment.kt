package com.nexters.android.pliary.view.detail.diary.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nexters.android.pliary.R
import com.nexters.android.pliary.analytics.AnalyticsUtil
import com.nexters.android.pliary.analytics.FBEvents
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.databinding.FragmentDiaryLayoutBinding
import com.nexters.android.pliary.view.detail.DetailViewModel
import com.nexters.android.pliary.view.detail.bottom.fragment.DetailBottomFragment
import com.nexters.android.pliary.view.detail.diary.adapter.DetailDiaryAdapter
import com.nexters.android.pliary.view.detail.diary.data.DiaryData
import com.nexters.android.pliary.view.detail.diary.viewmodel.DetailDiaryViewModel
import com.nexters.android.pliary.view.main.MainViewModel
import com.nexters.android.pliary.view.util.CardItemDecoration
import com.nexters.android.pliary.view.util.eventObserver
import com.nexters.android.pliary.view.util.getFirstMetDDay
import com.nexters.android.pliary.view.util.todayValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class DetailDiaryFragment : BaseFragment<DetailDiaryViewModel>() {

    private val TAG = this::class.java.simpleName

    @Inject
    lateinit var diaryAdapter : DetailDiaryAdapter
    private lateinit var mainVM : MainViewModel
    private lateinit var bottomVM : DetailViewModel
    lateinit var binding : FragmentDiaryLayoutBinding

    private var cardID : Long = -1
    private val diaryList = arrayListOf<DiaryData>()

    override fun getModelClass(): Class<DetailDiaryViewModel> = DetailDiaryViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /*return if(::binding.isInitialized) {
            binding.root
        } else {
            mainVM = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary_layout, container, false)
            with(binding) {
                root
            }
        }*/
        mainVM = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
        parentFragment?.parentFragment?.parentFragment?.let {
            if(it is DetailBottomFragment) bottomVM = ViewModelProviders.of(it).get(DetailViewModel::class.java)
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary_layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardID = mainVM.cardLiveID

        initView()
        getData()
    }

    private fun getData() {
        viewModel.localDataSource.diaries(cardID).observe(viewLifecycleOwner, Observer {
            viewModel.reqDiaryList(it)
        })

        viewModel.diaryList.observe(viewLifecycleOwner, eventObserver {
            diaryList.clear()
            diaryList.add(DiaryData.DateCount(
                dateCount = getFirstMetDDay(mainVM.plantLiveData.takeDate ?: todayValue()), //plant.takeDate,
                nickName = mainVM.plantLiveData.nickName
            ))
            diaryList.addAll(it)
            diaryAdapter.submitList(diaryList)
            binding.tvEmpty.isVisible = diaryList.count() <= 1
            diaryAdapter.notifyDataSetChanged()
        })

        viewModel.diaryClickEvent.observe(viewLifecycleOwner, Observer {
            bottomVM.onClickDiaryCard(it)
        })
    }

    private fun initView() {
        binding.rvDiary.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = diaryAdapter
            setHasFixedSize(true)
            addItemDecoration(CardItemDecoration(15))
        }
        diaryAdapter.setCallbacks(object : DetailDiaryAdapter.Callbacks{
            override fun onClickDiaryCard(id: Long) {
                viewModel.onClickDiary(id)
                AnalyticsUtil.event(FBEvents.DETAIL_DIARY_CLICK)
            }

            override fun onClickMenu(view : View, id: Long) {
                val popup = PopupMenu(context, view)
                activity?.menuInflater?.inflate(R.menu.card_menu, popup.menu)
                popup.apply {
                    setOnMenuItemClickListener {
                        when(it.itemId) {
                            R.id.modify -> {
                                bottomVM.onClickDiaryModify(id)
                                true
                            }
                            R.id.delete -> {
                                showDeleteDialog(id)
                                AnalyticsUtil.event(FBEvents.DETAIL_DIARY_DELETE_CLICK)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        })
    }

    private fun showDeleteDialog(id: Long) {
        context?.let {
            androidx.appcompat.app.AlertDialog.Builder(it, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setMessage(getString(R.string.diary_delete_message))
                .setCancelable(false)
                .setPositiveButton(R.string.delete) { _, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.localDataSource.deleteDiary(id)
                    }}
                .setNegativeButton(R.string.cancel) { i, a -> i.dismiss() }
                .show()
        }
    }
}