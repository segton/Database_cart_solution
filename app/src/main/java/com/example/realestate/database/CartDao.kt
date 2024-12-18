package com.example.realestate.database

import androidx.room.*
import com.example.realestate.Cart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    suspend fun getAllItems(): List<Cart>

    @Query("SELECT * FROM cart WHERE name LIKE :name")
    suspend fun getItemByName(name: String?): List<Cart>

    @Query("SELECT * FROM cart WHERE category LIKE :category")
    suspend fun getItemByCategory(category: String?): List<Cart>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg cart: Cart) : List<Long>

    @Delete
    suspend fun delete(field: Cart)

    @Query("DELETE FROM cart")
    suspend fun deleteAll()
}