package com.example.realestate.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.realestate.AppSingleton
import com.example.realestate.Item
import com.example.realestate.ItemDetailActivity
import com.example.realestate.ItemDiffCallback
import com.example.realestate.databinding.ItemCardBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemAdapter(onViewItemListener: OnViewItemListener) : ListAdapter<Item, ItemViewHolder>(ItemDiffCallback()) {

    private var listener = onViewItemListener

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val variantViewHolder = ItemViewHolder(binding)
        variantViewHolder.itemView.setOnClickListener {
            listener.onViewItem(getItem(variantViewHolder.adapterPosition), variantViewHolder.adapterPosition)
        }

        variantViewHolder.binding.addItem.setOnClickListener {
            val cart = getItem(variantViewHolder.adapterPosition).toCart()
            cart.quantity = 1

            GlobalScope.launch(Dispatchers.IO) {
                AppSingleton.getInstance().getCartDao().insertAll(cart)
                withContext(Dispatchers.Main) {
                    notifyItemChanged(variantViewHolder.adapterPosition)
                    Toast.makeText(parent.context, "${cart.name} has been added into cart.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return variantViewHolder
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnViewItemListener{
        fun onViewItem(item: Item, position: Int)
    }
}

class ItemViewHolder (val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
    @OptIn(DelicateCoroutinesApi::class)
    fun bind(item: Item){

        item.let {
            // Fill up the card with item data
            binding.itemName.text = item.name
            binding.unit.text = item.unit
            binding.itemPrice.text = item.priceLow

            // Set the image
            binding.itemImage.setImageResource(it.imageResId)

            GlobalScope.launch(Dispatchers.IO) {
                //check if this item is already added into cart
                val carts = AppSingleton.getInstance().getCartDao().getItemByName(item.name)
                withContext(Dispatchers.Main) {
                    if(carts.isEmpty())
                        binding.addItem.visibility = View.VISIBLE
                    else
                        binding.addItem.visibility = View.INVISIBLE
                }
            }
        }
    }
}