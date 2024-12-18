package com.example.realestate.database

import android.content.Context
import androidx.room.*
import com.example.realestate.Cart
import com.example.realestate.Item

@Database(entities = [Item::class, Cart::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun cartDao(): CartDao

    companion object{
        var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase?{
            if(INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "sd_db")
                        .fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }

        fun destroyDb(){
            INSTANCE = null
        }
    }
}