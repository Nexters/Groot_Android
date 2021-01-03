package com.nexters.android.pliary.view.home.adapter

import android.animation.Animator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nexters.android.pliary.R
import com.nexters.android.pliary.view.util.DialogFactory
import com.nexters.android.pliary.data.PlantCard
import com.nexters.android.pliary.data.PlantCard.Companion.EMPTY_CARD
import com.nexters.android.pliary.data.PlantCard.Companion.PLANT_CARD
import com.nexters.android.pliary.data.PlantCard.EmptyCard
import com.nexters.android.pliary.data.PlantCard.PlantCardItem
import com.nexters.android.pliary.data.toUIData
import com.nexters.android.pliary.databinding.EmptyCardItemBinding
import com.nexters.android.pliary.databinding.PlantCardItemBinding
import com.nexters.android.pliary.db.LocalDataSource
import com.nexters.android.pliary.db.entity.Plant
import com.nexters.android.pliary.view.home.HomeViewModel
import com.nexters.android.pliary.view.home.holder.PlantCardViewModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.empty_card_item.view.*
import kotlinx.android.synthetic.main.plant_card_item.view.*
import javax.inject.Inject

internal class HomeCardAdapter @Inject constructor(
    private val plantVM: PlantCardViewModel)
    : ListAdapter<PlantCard, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<PlantCard>() {
    override fun areItemsTheSame(oldItem: PlantCard, newItem: PlantCard): Boolean {
        return oldItem.listItemId == newItem.listItemId
    }

    override fun areContentsTheSame(oldItem: PlantCard, newItem: PlantCard): Boolean {
        return oldItem.listItemId == newItem.listItemId
    }

})  {

    interface Callbacks {
        fun onClickCardDetail(sharedElements: ArrayList<Pair<View, String>?>, position: Int)
        fun onClickAddCard()
        fun onClickWatering(plantCardId: Long)
    }
    private var callbacks: Callbacks? = null

    override fun getItemViewType(position: Int) = currentList[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when(viewType) {
            PLANT_CARD -> {
                val binding = DataBindingUtil.inflate<PlantCardItemBinding>(inflater, R.layout.plant_card_item, parent, false)
                CardViewHolder(binding, plantVM, callbacks).setOnClickViewHolder()
            }
            EMPTY_CARD -> {
                val binding = DataBindingUtil.inflate<EmptyCardItemBinding>(inflater, R.layout.empty_card_item, parent, false)
                EmptyViewHolder(binding).setOnClickViewHolder()
            }
            else -> {
                val binding = DataBindingUtil.inflate<EmptyCardItemBinding>(inflater, R.layout.empty_card_item, parent, false)
                EmptyViewHolder(binding).setOnClickViewHolder()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is CardViewHolder -> holder.bindView(currentList[position] as PlantCardItem)
            is EmptyViewHolder -> holder.bindView(currentList.size)
        }
    }

    private fun RecyclerView.ViewHolder.setOnClickViewHolder() : RecyclerView.ViewHolder{
        when(this) {
            is EmptyViewHolder -> itemView.setOnClickListener {
                callbacks?.onClickAddCard()
            }
            is CardViewHolder -> itemView.setOnClickListener {
                itemView.ivPlant.transitionName = "trans_card_detail"
                itemView.ibtnWater.transitionName = "trans_card_detail_water"
                itemView.clDday.transitionName = "trans_card_detail_dday"
                val extra : ArrayList<Pair<View, String>?> = arrayListOf(
                    ViewCompat.getTransitionName(itemView.ivPlant)?.let{ itemView.ivPlant to it },
                    ViewCompat.getTransitionName(itemView.ibtnWater)?.let { itemView.ibtnWater to it },
                    ViewCompat.getTransitionName(itemView.clDday)?.let { itemView.clDday to it }
                )
                callbacks?.onClickCardDetail(extra, adapterPosition)
            }
        }
        return this
    }

    fun setCallbacks(callbacks: Callbacks) {
        this.callbacks = callbacks
    }
}

internal class CardViewHolder(private val binding: PlantCardItemBinding,
                              private val plantVM: PlantCardViewModel,
                              private val callbacks: HomeCardAdapter.Callbacks? = null)
: RecyclerView.ViewHolder(binding.root), DialogFactory.WateringDialogListener {
    lateinit var plantCard : Plant

    fun bindView(data : PlantCardItem) {
        plantCard = data.plant
        binding.item = data.plant.toUIData()

        binding.ibtnWater.setOnClickListener {
            DialogFactory.showWateringDialog(binding.root.context, this)
            callbacks?.onClickWatering(plantCard.id)
        }
    }

    override fun onWatering() {
        binding.lottiePlant.apply {
            setAnimation("lottie/and_water.json")
            playAnimation()
            addAnimatorListener(object : Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    plantVM.onWateringPlant()
                }
            })
        }
    }

    override fun onDelay(day: Int) {
        plantVM.onDelayWateringDate(day)
    }

    private fun setGIF() {
        // 파일 S3 경로 "https://dailyissue.s3.ap-northeast-2.amazonaws.com/[gif파일명]"

        Glide.with(binding.ivPlant.context)
            .asGif()
            .load("https://dailyissue.s3.ap-northeast-2.amazonaws.com/And_Posi_Eucalyptus.gif")
            .placeholder(R.drawable.plant_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(binding.ivPlant)
    }
}

internal class EmptyViewHolder(private val binding: EmptyCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindView(count: Int) {
        binding.tvAdMessage.isVisible = count > 5
    }
}