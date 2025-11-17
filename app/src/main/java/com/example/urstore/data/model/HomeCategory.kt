package com.example.urstore.data.model

import com.example.urstore.presentation.home.HomeIntent

data class HomeCategory(
    val id : Int = 0,
    val category : String = "",
    val isClicked: Boolean = false
)