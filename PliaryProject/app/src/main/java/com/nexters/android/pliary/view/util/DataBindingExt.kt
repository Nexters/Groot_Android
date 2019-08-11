package com.nexters.android.pliary.view.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


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