package com.nexters.android.pliary.view.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.nexters.android.pliary.R
import com.nexters.android.pliary.data.getLocalImage
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter


@BindingAdapter("android:visibility")
internal fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if(isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("srcCompat")
internal fun setSrcCompat(imageView: ImageView, url: String?) {
    url?.apply {
        Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions().centerCrop())
            .into(imageView)
    }
}

@BindingAdapter(value =["srcCompatGif", "isPositive"], requireAll = false)
internal fun setSrcCompatGif(imageView: ImageView, url: String?, isPositive: Boolean) {
    val drawable = if(url.isNullOrEmpty()) R.drawable.and_posi_placeholer else url.getLocalImage(isPositive)
    url?.apply {
        Glide.with(imageView.context)
            .asGif()
            .load("https://dailyissue.s3.ap-northeast-2.amazonaws.com/${url}.gif")
            .placeholder(drawable)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(imageView)
    }
}

@BindingAdapter("lottie_fileName")
internal fun setLottieFilename(view: LottieAnimationView, fileName: String) {
    view.setAnimation(fileName)
}

@BindingAdapter("textDate")
internal fun setTextDate(textView: TextView, date: ZonedDateTime?) {
    date?.let {
        textView.text = DateTimeFormatter.ofPattern("YY.MM.dd").format(it)
    }
}


@BindingAdapter(value = [
    "textDDay", "dayTerm"
], requireAll = false)
internal fun setTextDDay(textView: TextView, lastWatered: String?, dayTerm: Int) {
    lastWatered?.let {
        textView.text = getWateredDDay(lastWatered, dayTerm)
    }
}
