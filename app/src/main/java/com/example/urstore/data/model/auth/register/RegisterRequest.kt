package com.example.urstore.data.model.auth.register

data class RegisterRequest(
    var firstName : String = "",
    var lastName : String = "",
    var email : String = "",
    var phoneNumber : String = "",
    var password : String = "",
    var address : String = ""
)