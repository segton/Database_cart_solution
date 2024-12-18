package com.example.realestate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.realestate.AppSingleton
import com.example.realestate.Item
import com.example.realestate.ItemRepository
import com.example.realestate.base.BaseViewModel
import kotlinx.coroutines.*

open class AddViewModel : BaseViewModel() {

    var items = MutableLiveData<List<Item>>()

    fun getAllItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val i = AppSingleton.getInstance().getItemDao().getAllItems()
            withContext(Dispatchers.Main) {
                //do something on front end
                items.value = i
            }
        }
    }

    fun getAllItemsByName(name: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val i = AppSingleton.getInstance().getItemDao().getItemByName(name)
            withContext(Dispatchers.Main) {
                //do something on front end
                items.value = i
            }
        }
    }

    fun getAllItemsByCategory(category: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val i = AppSingleton.getInstance().getItemDao().getItemByCategory(category)
            withContext(Dispatchers.Main) {
                //do something on front end
                items.value = i
            }
        }
    }

    fun saveItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            AppSingleton.getInstance().getItemDao().insertAll(item)
            withContext(Dispatchers.Main) {
                //do something on front end like show updated listing
            }
        }
    }
}