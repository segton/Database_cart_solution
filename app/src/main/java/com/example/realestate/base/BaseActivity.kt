package com.example.realestate.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding>: AppCompatActivity()  {

    lateinit var binding: T
    abstract fun getLayoutId(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateFontScale(this)
        binding = getLayoutId()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun updateFontScale(context: Context?): Context?{
        val configuration = context?.resources?.configuration
        configuration?.fontScale = 1.15f //0.85 small size, 1 normal size, 1,15 big etc

        return if(null != configuration)
            context.createConfigurationContext(configuration)
        else
            context
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(updateFontScale(newBase))
    }
}