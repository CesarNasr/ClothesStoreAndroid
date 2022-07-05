package com.example.clothesstoreapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesstoreapp.databinding.ListItemWishlistBinding
import com.example.clothesstoreapp.data.model.Product

class WishListAdapter : RecyclerView.Adapter<WishListAdapter.DataViewHolder>() {
//    var dataList = mutableListOf<Product>()


    private val differ = AsyncListDiffer(this, DiffUtilCallback.callback)

    fun getList(): MutableList<Product> = differ.currentList
    fun setList(list: MutableList<Product>) = differ.submitList(list)




    private var onItemClickListener: ((Product) -> Unit)? = null

    fun setOnItemClickListener(listener: (Product) -> Unit) {
        onItemClickListener = listener
    }


//    private fun getList() = dataList
//    fun setList(list: MutableList<Product>) {
//        dataList = list
//        notifyDataSetChanged()
//    }

    fun removeItem(item: Product) {
        val dataList = getList().toMutableList()
        dataList.remove(item)
        setList(dataList)
    }

    fun removeItemAt(position: Int) {
        val dataList = getList().toMutableList()
        dataList.removeAt(position)
        setList(dataList)
    }

    inner class DataViewHolder(private val binding: ListItemWishlistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            binding.product = item
            binding.executePendingBindings()
            binding.addToBasket.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            ListItemWishlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getList()[position])
    }

    override fun getItemId(position: Int): Long {
        return getList()[position].productId.toLong()
    }

    override fun getItemCount(): Int = getList().size
}