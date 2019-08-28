package com.nexters.android.pliary.view.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.DialogFactory
import com.nexters.android.pliary.data.PlantCard
import com.nexters.android.pliary.data.PlantCard.Companion.EMPTY_CARD
import com.nexters.android.pliary.data.PlantCard.Companion.PLANT_CARD
import com.nexters.android.pliary.data.PlantCard.EmptyCard
import com.nexters.android.pliary.data.PlantCard.PlantCardItem
import com.nexters.android.pliary.databinding.PlantCardItemBinding
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.plant_card_item.view.*

internal class HomeCardAdapter : ListAdapter<PlantCard, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<PlantCard>() {
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
    }
    private var callbacks: Callbacks? = null

    /*private var cardList : ArrayList<PlantCard> = arrayListOf()

    override fun getItemCount(): Int = cardList.size

    fun getCardList() : ArrayList<PlantCard> = cardList

    fun setCardList(list: ArrayList<PlantCard>) {
        if(cardList.isNotEmpty()) {
            cardList.clear()
        }
        cardList = arrayListOf<PlantCard>()
        cardList.addAll(list)
        cardList.add(EmptyCard())
    }*/

    override fun getItemViewType(position: Int) = currentList[position].type //cardList[position].type

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
            is CardViewHolder -> holder.bindView(currentList[position] as PlantCardItem)
            is EmptyViewHolder -> holder.bindView(currentList[position] as EmptyCard)
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


    inner class CardViewHolder(private val binding: PlantCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(data : PlantCardItem) {
            binding.item = data
            //setGIF()

            binding.ibtnWater.setOnClickListener { DialogFactory.showWateringDialog(binding.root.context) }
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

    inner class EmptyViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindView(data : EmptyCard) {

        }
    }
}