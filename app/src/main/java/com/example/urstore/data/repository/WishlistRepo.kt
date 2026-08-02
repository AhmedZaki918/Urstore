package com.example.urstore.data.repository

import com.example.urstore.data.local.CoffeeDao
import com.example.urstore.data.local.CoffeeEntity
import com.example.urstore.data.model.drinks.ItemDetails
import javax.inject.Inject

class WishlistRepo @Inject constructor(
    private val coffeeDao: CoffeeDao
) {
    suspend fun addToWishlist(coffee: ItemDetails) {
        val item = CoffeeEntity(
            id = coffee.id,
            name = coffee.title,
            caption = coffee.description,
            rating = coffee.rate,
            price = coffee.price,
            itemImage = coffee.imageName
        )
        coffeeDao.insert(item)
    }

    suspend fun displayWishlist(): List<CoffeeEntity> {
        return coffeeDao.getAll()
    }

    suspend fun isItemSaved(id: Int): Boolean {
        return coffeeDao.isCoffeeExists(id)
    }

    suspend fun delete(coffee: ItemDetails) {
        val item = CoffeeEntity(
            id = coffee.id,
            name = coffee.title,
            caption = coffee.description,
            rating = coffee.rate,
            price = coffee.price,
            itemImage = coffee.imageName
        )
        coffeeDao.delete(item)
    }

    suspend fun delete(coffee: CoffeeEntity) {
        coffeeDao.delete(coffee)
    }
}