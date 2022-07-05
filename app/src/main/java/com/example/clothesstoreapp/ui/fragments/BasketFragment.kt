package com.example.clothesstoreapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesstoreapp.R
import com.example.clothesstoreapp.databinding.FragmentBasketBinding
import com.example.clothesstoreapp.data.model.Product
import com.example.clothesstoreapp.ui.adapters.BasketAdapter
import com.example.clothesstoreapp.ui.utils.UiState
import com.example.clothesstoreapp.ui.viewmodels.BasketViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BasketFragment : Fragment() {
    @Inject
    lateinit var basketAdapter: BasketAdapter

    private lateinit var binding: FragmentBasketBinding
    private val viewModel: BasketViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_basket,
            container,
            false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewModel.fetchBasketProducts()
        collectUiStates()
    }


    private fun collectUiStates() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.basketUiState.collect {
                    if (it is UiState.Loaded) {
                        if (it.data.isNullOrEmpty()) {
                            // show empty error if needed
                        } else {
                            basketAdapter.setList(it.data as MutableList<Product>)
                        }
                    } else {
                        // show error if needed
                    }
                }

            }

        }
    }


    private fun initRecyclerView() {
        binding.basketRecyclerview.apply {
            adapter = basketAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        basketAdapter.setOnItemClickListener {
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.basketRecyclerview)
    }

    private val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.item_deleted),
                Toast.LENGTH_SHORT
            ).show()
            //Remove swiped item from DB and notify the RecyclerView on Success
            val position = viewHolder.adapterPosition
            viewModel.deleteBasketProducts(basketAdapter.getList()[position].productId.toInt()) { products ->
                basketAdapter.setList(products as MutableList<Product>)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BasketFragment()
    }
}