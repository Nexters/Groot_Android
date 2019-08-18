package com.nexters.android.pliary.view.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.nexters.android.pliary.R


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

@BindingAdapter("srcCompatGif")
internal fun setSrcCompatGif(imageView: ImageView, url: String?) {
    url?.apply {
        Glide.with(imageView.context)
            .asGif()
            .load("https://dailyissue.s3.ap-northeast-2.amazonaws.com/${url}.gif")
            .placeholder(R.drawable.and_posi_placeholer)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(imageView)
    }
}

@BindingAdapter("lottie_fileName")
internal fun setLottieFilename(view: LottieAnimationView, fileName: String) {
    view.setAnimation(fileName)
}