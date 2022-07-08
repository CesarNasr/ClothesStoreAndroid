package com.example.clothesstoreapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.clothesstoreapp.R
import com.example.clothesstoreapp.data.model.Product
import com.example.clothesstoreapp.databinding.ActivityMainBinding
import com.example.clothesstoreapp.ui.utils.UiState
import com.example.clothesstoreapp.ui.viewmodels.MainViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()

        setupNavController()

    }

    private fun observeBadgeCounters(navView: BottomNavigationView) {
        val wishlistMenuItemId: Int = navView.menu.getItem(1).itemId //wishlist item
        val basketMenuItemId: Int = navView.menu.getItem(2).itemId //basket item
        val wishListBadge = navView.getOrCreateBadge(wishlistMenuItemId)
        val basketBadge = navView.getOrCreateBadge(basketMenuItemId)

        collectUiStates(wishListBadge, basketBadge)
    }


    private fun collectUiStates(wishListBadge: BadgeDrawable, basketBadge: BadgeDrawable) {

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    mainViewModel.wishListCount.collect {
                        if (it == 0 || it == null) {
                            wishListBadge.isVisible = false   // hide badge
                            wishListBadge.clearNumber()
                        } else {
                            wishListBadge.isVisible = true    // show badge
                            wishListBadge.number = it
                        }
                    }
                }
                launch {
                    mainViewModel.basketCount.collect {
                        if (it == 0 || it == null) {
                            basketBadge.isVisible = false   // hide badge
                            basketBadge.clearNumber()
                        } else {
                            basketBadge.isVisible = true  // show badge
                            basketBadge.number = it
                        }
                    }
                }

            }
        }
    }


    private fun setupNavController() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navView: BottomNavigationView = findViewById(R.id.bottomNavBar)
        navView.setupWithNavController(navController)

        observeBadgeCounters(navView)
    }
}