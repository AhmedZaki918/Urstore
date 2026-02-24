package com.example.urstore.data.local

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val PRODUCT_ID = "product_Id"
    const val ADDED_TO_CART = "added_to_cart"
    const val EXIST_IN_CART = "exist_in_cart"
    const val BASE_URL = "https://rac.arrowtecheg.com/api/"

    const val PREFERENCE_NAME = "shop_preferences"
    val F_NAME_KEY = stringPreferencesKey(name = "first_name")
    val L_NAME_KEY = stringPreferencesKey(name = "last_name")
    val TOKEN = stringPreferencesKey(name = "token")
}