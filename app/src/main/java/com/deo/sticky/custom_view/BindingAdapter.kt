package com.deo.sticky.custom_view

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("is_visible")
internal fun setVisible(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("image_src")
internal fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("background")
internal fun setBackgroundResource(view: View, resource: Int) {
    view.setBackgroundResource(resource)
}
