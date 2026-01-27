package com.example.urstore.data.repository

import com.example.urstore.data.model.Cart
import com.example.urstore.data.model.drinks_dto.DrinksDataDto

class CartRepo {
    val cartData = ArrayList<Cart>()

    fun addToCart(item: DrinksDataDto) {
        cartData.add(
            Cart(
                image = item.imageName,
                qty = 1,
                unitPrice = item.price,
                totalPrice = item.price,
                id = item.id,
                title = item.title,
                rate = item.rate,
                description = item.description,
                caption = ""
            )
        )
    }


    fun fetchCart(): List<Cart> {
        return cartData
    }

    fun isItemInCart(id: Int): Boolean {
        var isItemExist = false

        for (item in cartData) {
            if (item.id == id) {
                isItemExist = true
                break
            }
        }
        return isItemExist
    }


    fun removeItemFromCart(item: Cart) {
        cartData.remove(item)
    }


    fun increaseQuantity(id: Int) {
        for (item in cartData) {
            if (item.id == id) {
                item.qty++
                item.totalPrice = calculateTotalPricePerItem(item.qty,item.unitPrice)
                break
            }
        }
    }

    fun decreaseQuantity(id: Int) {
        for (item in cartData) {
            if (item.id == id && item.qty > 1) {
                item.qty--
                item.totalPrice = calculateTotalPricePerItem(item.qty,item.unitPrice)
                break
            }
        }
    }

    private fun calculateTotalPricePerItem(
        qty: Int,
        unitPrice: Int
    ): Int {
        return (qty * unitPrice)
    }
}