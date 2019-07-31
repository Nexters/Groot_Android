package com.nexters.android.pliary.view.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexters.android.pliary.R
import com.nexters.android.pliary.data.PlantCardDummy
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.plant_card_item.view.*

class HomeCardAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var cardList : ArrayList<PlantCardDummy>

    override fun getItemCount(): Int = cardList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plant_card_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CardViewHolder).bindView(cardList[position])
    }

    inner class CardViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindView(data : PlantCardDummy) {
            containerView.tvNickname.text = "${data.plantNickname}에게 물주기"
            containerView.tvDDay.text = "${data.plantDate}"

        }
    }
}