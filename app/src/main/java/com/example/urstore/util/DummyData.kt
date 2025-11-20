package com.example.urstore.util

import com.example.urstore.R
import com.example.urstore.data.model.HomeCategory
import com.example.urstore.data.model.HomePopular
import com.example.urstore.data.model.ProductSize


fun homePopularDummy(): List<HomePopular> {
    val homePopular = ArrayList<HomePopular>()

    homePopular.add(
        HomePopular(
            id = 0,
            image = R.drawable.drink_1,
            title = "Cappuccino",
            caption = "Cappuccino, Milk Foam, Cocoa Powder",
            description = "Experience the perfect cappuccino in every cup. Bold, rich, and aromatic, our cappuccino is crafted to awaken your senses and give you that instant boost of energy. Whether you’re starting your day or taking a well-deserved break.",
            price = 4.5,
            rate = 4.0
        )
    )

    homePopular.add(
        HomePopular(
            id = 1,
            image = R.drawable.drink_2,
            title = "Espresso",
            caption = "Espresso, Bold, Rich",
            description = "Experience the perfect espresso in every cup. Bold, Bold, and aromatic, our espresso is crafted to awaken your senses and give you that instant boost of energy",
            price = 2.5,
            rate = 4.5
        )
    )
    return homePopular
}


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


fun homeCategoriesDummy(): List<HomeCategory> {
    val homeCategory = ArrayList<HomeCategory>()

    homeCategory.add(
        HomeCategory(
            id = 101,
            category = "Espresso"
        )
    )
    homeCategory.add(
        HomeCategory(
            id = 102,
            category = "Cappuccino",
            isClicked = true
        )
    )
    homeCategory.add(
        HomeCategory(
            id = 103,
            category = "Latte"
        )
    )
    homeCategory.add(
        HomeCategory(
            id = 104,
            category = "Mocha"
        )
    )
    homeCategory.add(
        HomeCategory(
            id = 105,
            category = "Hot Chocolate"
        )
    )
    return homeCategory
}
