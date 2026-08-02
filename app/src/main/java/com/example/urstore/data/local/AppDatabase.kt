package com.example.urstore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CoffeeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coffeeDao(): CoffeeDao
}