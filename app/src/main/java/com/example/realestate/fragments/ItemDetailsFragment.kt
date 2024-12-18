package com.example.realestate.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.realestate.R
import com.example.realestate.databinding.FragmentItemDetailsBinding
import com.example.realestate.Item
import com.example.realestate.ItemRepository

private typealias priceCardList = Pair<Float, View>

class ItemDetailsFragment : Fragment() {

    private var _binding: FragmentItemDetailsBinding? = null //we must name xml files like: fragment_A, and Android studio will automatically generate class called AFragment.
    private val binding get() = _binding!!
    private var item: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the item passed from the activity and show the details. bcs the detail has to depend on which item user selected
        arguments?.getString("item")?.let {
            val itemDetails = ItemRepository.getItemByName(it)

            //filling up the xml template with the item details
            itemDetails?.let{
                binding.itemName.text = it.name
                binding.itemImage.setImageResource(it.imageResId)
                binding.unit.text = it.unit
                binding.priceLow.text = it.priceLow
                binding.priceHigh.text = it.priceHigh
                binding.jayaPrice.text = it.jayaPrice
                binding.vgPrice.text = it.vgPrice
                binding.lotusPrice.text = it.lotusPrice
                binding.bigPrice.text = it.bigPrice
                binding.aeonPrice.text = it.aeonPrice
            }
        }

        //navigates back
        binding.backIcon.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
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