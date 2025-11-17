package com.example.urstore.util

import com.example.urstore.data.model.HomeCategory

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