package com.example.clothesstoreapp.ui.composeuisample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.clothesstoreapp.ui.composeuisample.screens.MainScreen
import com.example.clothesstoreapp.ui.composeuisample.theme.ClothesStoreAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClothesStoreAppTheme {
                MainScreen()
            }
        }
    }
}