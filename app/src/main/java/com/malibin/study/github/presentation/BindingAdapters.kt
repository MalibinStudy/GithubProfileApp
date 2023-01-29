package com.malibin.study.github.presentation

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:imageUrl")
fun ImageView.bindingImageUrl(imageUrl: String?) {
    if (imageUrl == null) return
    Glide.with(this).load(imageUrl).into(this)
}

@BindingAdapter("app:isVisible")
fun View.bindingVisibility(isVisible: Boolean?) {
    this.visibility = if (isVisible == true) View.VISIBLE else View.GONE
}
