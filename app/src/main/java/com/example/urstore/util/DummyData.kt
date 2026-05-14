package com.example.urstore.util

import com.example.urstore.data.model.drinks.ProductSize


fun productSizeDummy(): List<ProductSize> {
    val sizes = ArrayList<ProductSize>()
    sizes.add(
        ProductSize(
            isPressed = true,
            id = 101,
            size = "Small"
        )
    )

    sizes.add(
        ProductSize(
            id = 102,
            size = "Medium"
        )
    )

    sizes.add(
        ProductSize(
            id = 103,
            size = "Large"
        )
    )

    return sizes
}