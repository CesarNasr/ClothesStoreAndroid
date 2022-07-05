package com.example.clothesstoreapp.ui.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snack(message: String) {
    val snack = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snack.show()
}