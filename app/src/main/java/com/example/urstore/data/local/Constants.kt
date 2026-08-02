package com.example.urstore.data.local

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val PRODUCT_ID = "product_Id"
    const val EMAIL_ADDRESS = "email_address"
    const val ORDER_ID = "order_id"
    const val OTP = "otp"
    const val ADDED_TO_CART = "added_to_cart"
    const val EXIST_IN_CART = "exist_in_cart"
    const val BASE_URL = "https://restaurant.arrowtecheg.com/api/"
    const val BEARER = "Bearer"

    // Room
    const val DATABASE_TABLE = "coffee_table"
    const val DATABASE_NAME = "coffee_database"

    // Data Store fields
    const val PREFERENCE_NAME = "shop_preferences"
    val F_NAME_KEY = stringPreferencesKey(name = "first_name")
    val L_NAME_KEY = stringPreferencesKey(name = "last_name")
    val DISPLAY_NAME = stringPreferencesKey(name = "display_name")
    val PHONE = stringPreferencesKey(name = "phone")
    val ADDRESS = stringPreferencesKey("address")
    val EMAIL = stringPreferencesKey("email")
    val TOKEN = stringPreferencesKey(name = "token")
    val CLIENT_ID = stringPreferencesKey(name = "client_id")
}