package com.example.urstore.presentation.checkout

sealed class CheckoutIntent {
    object GoBack : CheckoutIntent()
    object EditCart : CheckoutIntent()
    object PlaceOrder : CheckoutIntent()
    object SaveAddress : CheckoutIntent()
    object CancelAddress :  CheckoutIntent()

    data class ChangeAddress(var value: String) : CheckoutIntent()
    data class ChangePayment(val paymentType: String) : CheckoutIntent()
    data class ShowDialog(var isActive: Boolean) : CheckoutIntent()
}