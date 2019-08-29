package com.nexters.android.pliary.view.detail.top

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigator
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
import com.nexters.android.pliary.data.getLocalImage
import com.nexters.android.pliary.data.toUIData
import com.nexters.android.pliary.databinding.FragmentDetailBinding
import com.nexters.android.pliary.db.entity.Plant
import com.nexters.android.pliary.view.detail.DetailViewModel
import com.nexters.android.pliary.view.main.MainViewModel
import com.nexters.android.pliary.view.util.setGIF
import kotlinx.android.synthetic.main.fragment_detail.*

internal class DetailFragment  : BaseFragment<DetailViewModel>() {

    private val TAG = this.tag.toString()

    override fun getModelClass(): Class<DetailViewModel> = DetailViewModel::class.java

    private lateinit var mainVM : MainViewModel

    private val cardID : Long by lazy { arguments?.getLong("cardID") ?: 0L }
    private lateinit var binding : FragmentDetailBinding
    private var plantData : Plant? = null
    private var plantUIData : PlantCardUI? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if(::binding.isInitialized) {
            binding.root
        } else {
            mainVM = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
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
            ivBack.setOnClickListener { popBackStack() }
            ivArrowDown.setOnClickListener {
                navigate(R.id.action_detailFragment_to_detailBottomFragment,
                    Bundle().apply { putLong("cardID", cardID) },
                    null,
                    null)
            }
        } else {
            initView()
        }


    }



    private fun initObserver(){
        mainVM.cardLiveID = cardID
        viewModel.localDataSource.plant(cardID).observe(this, Observer {
            plantData = it
            plantUIData = it.toUIData()
            binding.item = it.toUIData()
            mainVM.plantLiveData = it
        })
    }

    private fun setBundleImage() {
        postponeEnterTransition()
        setGIF(plantUIData?.photoUrl, !(plantUIData?.isDayPast ?: false))
        startPostponedEnterTransition()
        initTransition()
    }

    private fun initTransition() {
        prepareTransitions()
        val trans = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        trans.addListener(object : androidx.transition.Transition.TransitionListener {
            override fun onTransitionEnd(transition: androidx.transition.Transition) {
                transition.removeListener(this)
                initView()
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
                ivBackGround?.apply { sharedElements?.put(transitionName, this) }
                ibtnWater?.apply { sharedElements?.put(transitionName, this) }
                clDday?.apply { sharedElements?.put(transitionName, this) }
            }
        })
    }


    private fun initView() {
        plantData?.let {
            binding.apply {
                ivBackGround.setGIF(plantUIData?.photoUrl, !(plantUIData?.isDayPast ?: false))
                tvPlantName.text = it.species?.name
                tvSpecies.text = it.species?.nameKr ?: ""
                tvNickname.text = it.nickName

            }
        }
    }

    private fun setGIF(url: String? = "", isPositive: Boolean){
        context?.let {
            val drawable = if(url.isNullOrEmpty()) R.drawable.and_posi_placeholer else url.getLocalImage(isPositive)

            Glide.with(it)
                .asGif()
                .load("https://dailyissue.s3.ap-northeast-2.amazonaws.com/${url}.gif")
                .placeholder(drawable)
                .centerCrop()
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
                        //initView()
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
                        //initView()
                        return false
                    }
                })
                .into(binding.ivBackGround)
        }
    }

}