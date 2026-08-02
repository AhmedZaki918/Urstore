package com.example.urstore.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.urstore.data.local.Constants.DATABASE_TABLE

@Entity(tableName = DATABASE_TABLE)
data class CoffeeEntity(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "caption") val caption : String,
    @ColumnInfo(name = "item_image") val itemImage : String,
    @ColumnInfo(name = "rating") val rating : String,
    @ColumnInfo(name = "price") val price : String
)
