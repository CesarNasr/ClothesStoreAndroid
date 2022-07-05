package com.example.clothesstoreapp.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.clothesstoreapp.data.model.Product

object DiffUtilCallback {

     val callback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}