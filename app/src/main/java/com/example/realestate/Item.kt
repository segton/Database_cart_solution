package com.example.realestate

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    val category: String,
    val name: String,
    val imageResId: Int,
    val unit: String,
    val priceLow: String,
    val priceHigh: String,
    val jayaPrice: String,
    val vgPrice: String,
    val lotusPrice: String,
    val bigPrice: String,
    val aeonPrice: String
){
    fun toCart(): Cart{
        return Cart(
            0,
            category,
            name,
            imageResId,
            unit,
            priceLow,
            priceHigh,
            jayaPrice,
            vgPrice,
            lotusPrice,
            bigPrice,
            aeonPrice
        )
    }
}

class ItemDiffCallback: DiffUtil.ItemCallback<Item>(){

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

}
