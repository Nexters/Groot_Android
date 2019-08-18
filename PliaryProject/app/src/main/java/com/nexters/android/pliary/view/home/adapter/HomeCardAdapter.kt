package com.nexters.android.pliary.view.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.DialogFactory
import com.nexters.android.pliary.data.PlantCard
import com.nexters.android.pliary.data.PlantCard.Companion.EMPTY_CARD
import com.nexters.android.pliary.data.PlantCard.Companion.PLANT_CARD
import com.nexters.android.pliary.data.PlantCard.EmptyCard
import com.nexters.android.pliary.data.PlantCard.PlantCardDummy
import com.nexters.android.pliary.databinding.PlantCardItemBinding
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.plant_card_item.view.*
import javax.inject.Inject

class HomeCardAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Callbacks {
        fun onClickCardDetail(sharedElements: ArrayList<Pair<View, String>?>)
        fun onClickAddCard()
    }
    private var callbacks: Callbacks? = null

    private var cardList : ArrayList<PlantCard> = arrayListOf()

    override fun getItemCount(): Int = cardList.size

    fun getCardList() : ArrayList<PlantCard> = cardList

    fun setCardList(list: ArrayList<PlantCard>) {
        if(cardList.isNotEmpty()) {
            cardList.clear()
        }
        cardList = arrayListOf<PlantCard>()
        cardList.addAll(list)
        cardList.add(EmptyCard())
    }

    override fun getItemViewType(position: Int) = cardList[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when(viewType) {
            PLANT_CARD -> {
                val binding = DataBindingUtil.inflate<PlantCardItemBinding>(inflater, R.layout.plant_card_item, parent, false)
                CardViewHolder(binding).setOnClickViewHolder()
            }
            EMPTY_CARD -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.empty_card_item, parent, false)
                EmptyViewHolder(view).setOnClickViewHolder()
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.empty_card_item, parent, false)
                EmptyViewHolder(view).setOnClickViewHolder()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is CardViewHolder -> holder.bindView(cardList[position] as PlantCardDummy)
            is EmptyViewHolder -> holder.bindView(cardList[position] as EmptyCard)
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
                callbacks?.onClickCardDetail(extra)
            }
        }
        return this
    }

    fun setCallbacks(callbacks: Callbacks) {
        this.callbacks = callbacks
    }


    inner class CardViewHolder(private val binding: PlantCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(data : PlantCardDummy) {
            binding.item = data
            setGIF()

            binding.ibtnWater.setOnClickListener { DialogFactory.showHouseHoldConfirmDlg(binding.root.context) }
        }

        private fun setGIF() {
            Glide.with(binding.ivPlant.context)
                .asGif()
                .load(R.raw.pliary_gif_test)
                .placeholder(R.drawable.plant_placeholder)
                .into(binding.ivPlant)
        }
    }

    inner class EmptyViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindView(data : EmptyCard) {

        }
    }
}