package com.example.urstore.util

import com.example.urstore.R
import com.example.urstore.data.model.HomeCategory
import com.example.urstore.data.model.HomePopular


fun homePopularDummy(): List<HomePopular> {
    val homePopular = ArrayList<HomePopular>()

    homePopular.add(
        HomePopular(
            image = R.drawable.drink_1,
            title = "Cappuccino",
            description = "Espresso, Milk Foam, Cocoa Powder",
            price = 4.5
        )
    )

    homePopular.add(
        HomePopular(
            image = R.drawable.drink_2,
            title = "Espresso",
            description = "Espresso",
            price = 2.5
        )
    )
    return homePopular
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