package com.example.realestate

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Cart(
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
    val aeonPrice: String,
    var quantity : Int = 0
)