package com.example.realestate.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.realestate.Item
import com.example.realestate.ItemDetailActivity
import com.example.realestate.ItemRepository
import com.example.realestate.R
import com.example.realestate.adapter.ItemAdapter
import com.example.realestate.base.BaseFragment
import com.example.realestate.databinding.FragmentAddBinding
import com.example.realestate.viewmodel.AddViewModel

class AddFragment : BaseFragment() {

    private val itemDetailsFragment = ItemDetailsFragment()
    private lateinit var binding: FragmentAddBinding

    override fun getLayoutId(): Int = R.layout.fragment_add
    override fun createViewModel() = AddViewModel()


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) { //now that the binding is done, Im setting up UI interactions and logic here
        super.onViewCreated(view, savedInstanceState) //mandatory
        binding = FragmentAddBinding.bind(view)
        viewModel = ViewModelProvider(this)[AddViewModel::class.java]

        //setting up colors for selected catogeries
        val textViewList = listOf(
            binding.fruitsTv,
            binding.veggiesTv,
            binding.meatTv,
            binding.fishTv,
            binding.seafoodTv,
            binding.dairyTv,
            binding.snacksTv
        )

        // setting up click listeners for each category
        textViewList.forEach { textView ->
            textView.setOnClickListener {
                // Set all TextViews to the original color
                textViewList.forEach { it.setTextColor(Color.parseColor("#34DC1E")) }

                // Set the clicked TextView's color to the selected color
                textView.setTextColor(Color.parseColor("#007AFF"))

                //show the selected category
                when (it.id) {
                    binding.fruitsTv.id -> showItems("Fruits")
                    binding.veggiesTv.id -> showItems("Veggies")
                    binding.meatTv.id -> showItems("Meat")
                    binding.fishTv.id -> showItems("Fish")
                    binding.seafoodTv.id -> showItems("Seafood")
                    binding.dairyTv.id -> showItems("Eggs & Dairy")
                    binding.snacksTv.id -> showItems("Snacks")
                }

            }
        }

        //RecyclerView should be used when dealing with dynamic list. RecyclerView must come with adapter to bind the data with UI
        binding.itemListing.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = ItemAdapter(object: ItemAdapter.OnViewItemListener{
                override fun onViewItem(item: Item, position: Int) {
                    val intent = Intent(requireContext(), ItemDetailActivity::class.java)
                    intent.putExtra("item", item.name)
                    intent.putExtra("position", position)
                    resultLauncher.launch(intent)
                }
            })
        }

        showItems("Fruits")

        setLiveDataListener()
    }

    private fun showItems(category: String) {
        //Invoking function inside viewmodel, then observe the value on setLiveDataListener
        (viewModel as AddViewModel).getAllItemsByCategory(category)
    }

    private fun setLiveDataListener() {
        with(viewModel as AddViewModel) {
            items.observe(viewLifecycleOwner) {
                //this will refresh the UI
                Log.d("Item Count", "${it.size}")
                (binding.itemListing.adapter as ItemAdapter).submitList(it)
            }
        }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            result.data.let {
                val pos = it?.getIntExtra("position", -1) ?: -1
                if(pos >= 0)
                    (binding.itemListing.adapter as ItemAdapter).notifyItemChanged(pos)
            }
        }
    }
}