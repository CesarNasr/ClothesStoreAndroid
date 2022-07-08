package com.example.clothesstoreapp.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.clothesstoreapp.R
import com.example.clothesstoreapp.databinding.FragmentProductBottomSheetBinding
import com.example.clothesstoreapp.data.model.Product
import com.example.clothesstoreapp.ui.utils.UiState
import com.example.clothesstoreapp.ui.viewmodels.ProductDetailViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


private const val ARG_PRODUCT = "product"

@AndroidEntryPoint
class ProductBottomSheet : BottomSheetDialogFragment() {

    private var productItem: Product? = null
    private lateinit var binding: FragmentProductBottomSheetBinding

    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productItem = it.getSerializable(ARG_PRODUCT) as Product?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_product_bottom_sheet,
            container,
            false
        )

        binding.viewModel = viewModel
        binding.product = productItem
        binding.executePendingBindings()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeBtn.setOnClickListener {
            this.dismiss()
        }

        collectUiStates()
    }

    private fun collectUiStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.onItemInserted.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            )
                .collect {
                    if (it is UiState.Loaded) {
                        this@ProductBottomSheet.dismiss()
                    }
                }
        }
    }

    private lateinit var dialog: BottomSheetDialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog
    }

    //set the behavior here
    private fun setFullScreen() {
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onStart() {
        super.onStart()
        setFullScreen()//initiated at onActivityCreated(), onStart()
    }

    companion object {
        val TAG = "ProductBottomSheet"

        @JvmStatic
        fun newInstance(product: Product) =
            ProductBottomSheet().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PRODUCT, product)
                }
            }
    }
}