package com.example.realestate.database

import androidx.room.*
import com.example.realestate.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    suspend fun getAllItems(): List<Item>

    @Query("SELECT * FROM item WHERE name LIKE :name")
    suspend fun getItemByName(name: String?): List<Item>

    @Query("SELECT * FROM item WHERE category LIKE :category")
    suspend fun getItemByCategory(category: String?): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg item: Item) : List<Long>

    @Delete
    suspend fun delete(field: Item)

    @Query("DELETE FROM item")
    suspend fun deleteAll()
}