package com.example.clothesstoreapp.ui.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

// Binding adapter to show image using Glide
@BindingAdapter("app:imageURL")
fun setImageFromURL(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url)
        .into(imageView)
}