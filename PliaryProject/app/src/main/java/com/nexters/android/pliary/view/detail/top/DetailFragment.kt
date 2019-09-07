package com.nexters.android.pliary.view.detail.top

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.app.SharedElementCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.data.PlantCardUI
import com.nexters.android.pliary.data.toUIData
import com.nexters.android.pliary.databinding.FragmentDetailBinding
import com.nexters.android.pliary.db.entity.Plant
import com.nexters.android.pliary.view.detail.DetailViewModel
import com.nexters.android.pliary.view.main.MainViewModel
import com.nexters.android.pliary.view.util.DialogFactory
import com.nexters.android.pliary.view.util.getFutureWateringDate
import com.nexters.android.pliary.view.util.setGIF
import com.nexters.android.pliary.view.util.todayValue
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class DetailFragment  : BaseFragment<DetailViewModel>(), DialogFactory.WateringDialogListener  {

    private val TAG = this.tag.toString()

    override fun getModelClass(): Class<DetailViewModel> = DetailViewModel::class.java

    private lateinit var mainVM : MainViewModel

    private val cardID : Long by lazy { arguments?.getLong("cardID") ?: 0L }
    private val defaultImage : Int by lazy { arguments?.getInt("defaultImage") ?: 0 }
    private lateinit var binding : FragmentDetailBinding
    private var plantData : Plant? = null
    private var plantUIData : PlantCardUI? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if(::binding.isInitialized) {
            binding.root
        } else {
            mainVM = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
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

        if(plantData == null) {
            initObserver()
            setBundleImage()
            //initView()
        } else {
            initObserver()
            initView()
        }

        binding.ibtnWater.setOnClickListener {
            DialogFactory.showWateringDialog(binding.root.context, this)
        }
        binding.ivBack.setOnClickListener { popBackStack() }
        binding.ivArrowDown.setOnClickListener {
            navigate(R.id.action_detailFragment_to_detailBottomFragment,
                Bundle().apply { putLong("cardID", cardID) },
                null,
                null)
        }
    }

    override fun onWatering() {
        binding.lottieBackGround.apply {
            setAnimation("lottie/and_water.json")
            playAnimation()
            addAnimatorListener(object : Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    viewModel.onWateringPlant()
                }
            })
        }
    }

    override fun onDelay(day: Int) {
        viewModel.onDelayWateringDate(day)
    }

    private fun initObserver(){
        mainVM.cardLiveID = cardID
        viewModel.localDataSource.plant(cardID).observe(this, Observer {
            plantData = it
            plantUIData = it.toUIData()
            binding.item = it.toUIData()
            mainVM.plantLiveData = it
            if(defaultImage == 0) {
                initView()
                ivBackGround.setGIF(plantUIData?.photoUrl, !(plantUIData?.isDayPast ?: false))
            }
        })

        viewModel.wateringEvent.observe(this, Observer {
            plantData?.let {
                val job = CoroutineScope(Dispatchers.IO).launch {
                    viewModel.localDataSource.upsertPlants(it.apply {
                        lastWateredDate = todayValue()
                        val set = wateredDays.toHashSet()
                        set.add(todayValue())
                        val result = arrayListOf<String>()
                        set.forEach { result.add(it) }
                        wateredDays = result
                        willbeWateringDate = todayValue().getFutureWateringDate(it.waterTerm ?: 1)
                    })
                }
                reloadFragment()// initObserver()
            }

        })

        viewModel.delayDateEvent.observe(this, Observer {delay ->
            plantData?.let {
                val job = CoroutineScope(Dispatchers.IO).launch {
                    viewModel.localDataSource.upsertPlants(it.apply { willbeWateringDate = willbeWateringDate.getFutureWateringDate(delay) })
                }
                reloadFragment()// initObserver()
            }
        })

        viewModel.menuEvent.observe(this, Observer {
            val popup = PopupMenu(context, binding.ivMenu)
            activity?.menuInflater?.inflate(R.menu.card_menu, popup.menu)
            popup.apply {
                setOnMenuItemClickListener {
                    when(it.itemId) {
                        R.id.modify -> {
                            navigate(R.id.action_detailFragment_to_modifyFragment)
                            true
                        }
                        R.id.delete -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.localDataSource.deletePlant(cardID)
                                popBackStack()
                            }
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        })
    }

    private fun reloadFragment() {
        navigate(R.id.action_detailFragment_self,
            Bundle().apply { putLong("cardID", cardID) },
            NavOptions.Builder().setPopUpTo(R.id.detailFragment,true).build(),
            null)
    }

    private fun setBundleImage() {
        if(defaultImage != 0) {
            postponeEnterTransition()
            setGIF(plantUIData?.photoUrl)
            prepareTransitions()
            initTransition()
        }
        //startPostponedEnterTransition()
    }

    private fun initTransition() {
        val trans = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        trans.addListener(object : androidx.transition.Transition.TransitionListener {
            override fun onTransitionEnd(transition: androidx.transition.Transition) {
                transition.removeListener(this)
                ivBackGround.setGIF(plantUIData?.photoUrl, !(plantUIData?.isDayPast ?: false))
            }

            override fun onTransitionResume(transition: androidx.transition.Transition) {
            }

            override fun onTransitionPause(transition: androidx.transition.Transition) {
            }

            override fun onTransitionCancel(transition: androidx.transition.Transition) {
            }

            override fun onTransitionStart(transition: androidx.transition.Transition) {
            }
        })
        sharedElementEnterTransition = trans
    }

    private fun prepareTransitions() {
        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
                binding.ivBackGround?.apply { sharedElements?.put(transitionName, this) }
                binding.ibtnWater?.apply { sharedElements?.put(transitionName, this) }
                binding.clDday?.apply { sharedElements?.put(transitionName, this) }
            }
        })
    }


    private fun initView() {
        plantData?.let {
            binding.apply {
                //ivBackGround.setGIF(plantUIData?.photoUrl, !(plantUIData?.isDayPast ?: false))
                tvPlantName.text = it.species?.name
                tvSpecies.text = it.species?.nameKr ?: ""
                tvNickname.text = it.nickName
                tvDDay.text = plantUIData?.waterDday
            }
        }
    }

    private fun setGIF(url: String? = ""){
        context?.let {
            //val drawable = if(url.isNullOrEmpty()) R.drawable.and_posi_placeholer else url.getLocalImage(isPositive)

            Glide.with(it)
                .asGif()
                .load("https://dailyissue.s3.ap-northeast-2.amazonaws.com/${url}.gif")
                .placeholder(defaultImage)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .listener(object : RequestListener<GifDrawable> {
                    override fun onResourceReady(
                        resource: GifDrawable?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        startPostponedEnterTransition()
                        initView()
                        Log.d(TAG, "얍얍얍얍얍얍얍얍얍 Glide : onResoureReady")
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.ivBackGround?.let { it -> Glide.get(it.context).clearMemory() }
                        startPostponedEnterTransition()
                        initView()
                        Log.d(TAG, "얍얍얍얍얍얍얍얍얍 Glide : onLoadFailed ${e?.message}")
                        return false
                    }
                })
                .into(binding.ivBackGround)
        }
    }

}