package com.example.clothesstoreapp.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Product(
    @SerializedName("productId")
    val productId: String,
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("oldPrice")
    val oldPrice: Double? = null,
    @SerializedName("price")
    val price: Double,
    @SerializedName("stock")
    val stock: Int? = null,
    @SerializedName("qty")
    var qty: Int? = null
) : Serializable