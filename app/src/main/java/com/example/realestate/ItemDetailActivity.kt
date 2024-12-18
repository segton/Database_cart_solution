package com.example.realestate

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.coroutineScope
import com.example.realestate.base.BaseActivity
import com.example.realestate.databinding.FragmentItemDetailsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ItemDetailActivity : BaseActivity<FragmentItemDetailsBinding>() {
    override fun getLayoutId(): FragmentItemDetailsBinding = FragmentItemDetailsBinding.inflate(layoutInflater, null, false)

    private var cart : Cart? = null
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        position = intent.extras?.getInt("position", -1) ?: -1
        intent.extras?.getString("item")?.let {

            lifecycle.coroutineScope.launch {
                //Check if item already added into cart, else grab item
                val carts = AppSingleton.getInstance().getCartDao().getItemByName(it)
                if(carts.isEmpty()){
                    val items = AppSingleton.getInstance().getItemDao().getItemByName(it)
                    if(items.isNotEmpty()){
                        cart = items.first().toCart()
                    }
                }
                else{
                    cart = carts.first()
                }

                withContext(Dispatchers.Main) {
                    //do something on front end like show updated listing

                    cart.let {
                        binding.itemName.text = it?.name
                        binding.itemImage.setImageResource(it?.imageResId!!)
                        binding.unit.text = it.unit
                        binding.priceLow.text = it.priceLow
                        binding.priceHigh.text = it.priceHigh
                        binding.jayaPrice.text = it.jayaPrice
                        binding.vgPrice.text = it.vgPrice
                        binding.lotusPrice.text = it.lotusPrice
                        binding.bigPrice.text = it.bigPrice
                        binding.aeonPrice.text = it.aeonPrice

                        binding.quantityTxt.text = it.quantity.toString()
                    }
                }
            }
        }

        //navigates back
        binding.backIcon.setOnClickListener{
            val intent = Intent()
            intent.putExtra("position", position)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.addItem.setOnClickListener {
            var quantity = Integer.parseInt(binding.quantityTxt.text.toString())
            quantity += 1

            cart?.quantity = quantity
            lifecycle.coroutineScope.launch {
                AppSingleton.getInstance().getCartDao().insertAll(cart!!)
                withContext(Dispatchers.Main) {
                    binding.quantityTxt.text = quantity.toString()
                }
            }
        }

        binding.minusItem.setOnClickListener {
            var quantity = Integer.parseInt(binding.quantityTxt.text.toString())
            if(quantity != 0){
                quantity -= 1
                if(quantity == 0){
                    lifecycle.coroutineScope.launch {
                        AppSingleton.getInstance().getCartDao().delete(cart!!)
                        withContext(Dispatchers.Main) {
                            binding.quantityTxt.text = "0"

                            Toast.makeText(this@ItemDetailActivity, "${cart?.name} has been removed from cart", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    cart?.quantity = quantity
                    lifecycle.coroutineScope.launch {
                        AppSingleton.getInstance().getCartDao().insertAll(cart!!)
                        withContext(Dispatchers.Main) {
                            binding.quantityTxt.text = quantity.toString()
                        }
                    }
                }
            }
        }

        //setting up list pair of card & price, for sorting cards based on price order
        val priceCardList = listOf(
            Pair(binding.aeonPrice.text.substring(2).toFloat(), binding.aeonCard),
            Pair(binding.jayaPrice.text.substring(2).toFloat(), binding.jayaCard),
            Pair(binding.bigPrice.text.substring(2).toFloat(), binding.bigCard),
            Pair(binding.lotusPrice.text.substring(2).toFloat(), binding.lotusCard),
            Pair(binding.vgPrice.text.substring(2).toFloat(), binding.vgCard)
        )
        sortIncrease(priceCardList)

        //switch sortInc to sortDec
        binding.sort.setOnClickListener{
            if (binding.sort.tag == "increase"){ //if sort is currently inc, change to dec
                binding.sort.setImageResource(R.drawable.sort_decrease)
                binding.sort.tag = "decrease"
                sortDecrease(priceCardList)
            }
            else{
                binding.sort.setImageResource(R.drawable.sort_increase)
                binding.sort.tag = "increase"
                sortIncrease(priceCardList)
            }
        }
    }

    private fun sortIncrease(list: List<Pair<Float, View>>) {
        //sort low to high price
        val sortedPriceCardList= list.sortedBy { it.first }

        //remove all cards to rearrange later
        binding.cardLayout.removeAllViews()

        //set color of price tags, low:green, mid: orange, high: red
        sortedPriceCardList.forEachIndexed{ index, pair ->
            when(index){
                0 -> setPriceColor(pair.second.id, "#34DC1E") //first index: green
                sortedPriceCardList.size-1 -> setPriceColor(pair.second.id, "#FF0004") //last index: red
                else -> setPriceColor(pair.second.id, "#FF9900") //mid: orange
            }
        }
        //add cards with the sorted order
        sortedPriceCardList.forEach { pair ->
            binding.cardLayout.addView(pair.second)
        }
    }

    private fun sortDecrease(list: List<Pair<Float, View>>) {
        //sort high to low price
        val sortedPriceCardList = list.sortedByDescending{ it.first }

        //remove all cards to rearrange later
        binding.cardLayout.removeAllViews()

        //no need to set color again because the app sorts by increase by default, and in sortIncrease we have set up the color tags
        sortedPriceCardList.forEach { pair ->
            binding.cardLayout.addView(pair.second)
        }
    }

    private fun setPriceColor(id: Int, color: String){
        when(id){
            R.id.aeonCard -> binding.aeonPrice.setTextColor(Color.parseColor(color))
            R.id.jayaCard -> binding.jayaPrice.setTextColor(Color.parseColor(color))
            R.id.bigCard -> binding.bigPrice.setTextColor(Color.parseColor(color))
            R.id.lotusCard -> binding.lotusPrice.setTextColor(Color.parseColor(color))
            R.id.vgCard -> binding.vgPrice.setTextColor(Color.parseColor(color))
        }
    }

}