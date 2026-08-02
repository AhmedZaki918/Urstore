package com.example.urstore.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.urstore.data.local.Constants.DATABASE_TABLE

@Dao
interface CoffeeDao {
    @Query("SELECT * FROM $DATABASE_TABLE")
    suspend fun getAll() : List<CoffeeEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg coffee: CoffeeEntity)

    @Delete
    suspend fun delete(coffee: CoffeeEntity)

    @Query("DELETE FROM $DATABASE_TABLE")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT 1 FROM $DATABASE_TABLE WHERE id = :id)")
    suspend fun isCoffeeExists(id: Int): Boolean
}