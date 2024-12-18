package com.example.realestate

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.realestate.database.AppDatabase
import com.example.realestate.database.CartDao
import com.example.realestate.database.ItemDao

class AppSingleton: Application() {

    init {
        instance = this
    }

    private var appDatabase : AppDatabase? = null

    companion object {
        private var instance: AppSingleton? = null

        fun getInstance() : AppSingleton {
            return instance as AppSingleton
        }

        fun getInstance(context: Context) : AppSingleton {
            return context.applicationContext as AppSingleton
        }
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        appDatabase = AppDatabase.getDatabase(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

    }

    fun getItemDao(): ItemDao{
        return appDatabase!!.itemDao()
    }

    fun getCartDao(): CartDao{
        return appDatabase!!.cartDao()
    }


}