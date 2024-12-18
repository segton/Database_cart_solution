package com.example.realestate.base

import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.realestate.AppSingleton

open class BaseViewModel : ViewModel() {
    var isLoading = MutableLiveData<Boolean>(true)

    private var toast: Toast? = null
    fun toast(msg: String?) {
        if (!TextUtils.isEmpty(msg)) {
            toast?.cancel()
            toast = Toast.makeText(AppSingleton.getInstance(), msg, Toast.LENGTH_SHORT)
            toast?.show()
        }
    }
}