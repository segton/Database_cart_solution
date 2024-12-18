package com.example.realestate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.realestate.AppSingleton
import com.example.realestate.Item
import com.example.realestate.ItemRepository
import com.example.realestate.base.BaseViewModel
import kotlinx.coroutines.*

open class HomeViewModel : BaseViewModel() {

    var items = MutableLiveData<List<Item>>()

    fun flushItemsIntoDb(){
        isLoading.value = true

        //launch is part of coroutine that allows process to be ran asynchronously
        viewModelScope.launch(Dispatchers.IO) {
            //Check if db is empty, then flush all items into db
            val i = AppSingleton.getInstance().getItemDao().getAllItems()
            if(i.isEmpty())
                AppSingleton.getInstance().getItemDao().insertAll(*ItemRepository.items.toTypedArray())

            withContext(Dispatchers.Main) {
                isLoading.value = true
                //do something on front end
            }
        }
    }

    fun getAllItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val i = AppSingleton.getInstance().getItemDao().getAllItems()
            withContext(Dispatchers.Main) {
                //do something on front end
                items.value = i
            }
        }
    }

    fun saveItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            AppSingleton().getItemDao().insertAll(item)
            withContext(Dispatchers.Main) {
                //do something on front end like show updated listing
            }
        }
    }
}