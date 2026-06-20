package com.example.urstore.util

import android.media.Image
import com.example.urstore.R
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


data class OrderItems(
    val imageId: Int = 0,
    val title : String = ""
)

data class CurrentOrder(
    val orderItems : List<OrderItems> = emptyList(),
    val orderId : String = "",
    val dateAndTime : String = "",
    val address : String = "",
    val totalAmount : Double = 0.0,
    val statusCaption : String = "",
    val status : String = ""
)


fun allOrdersDummy(): List<CurrentOrder>{
    val orders = ArrayList<CurrentOrder>()

    orders.add(
        CurrentOrder(
            orderItems = listOf(
                OrderItems(
                    imageId = R.drawable.drink_1,
                    title = "Macchiato"
                ),

                OrderItems(
                    imageId = R.drawable.drink_2,
                    title = "Espresso"
                ),
                OrderItems(
                    imageId = R.drawable.drink_3,
                    title = "Pumpkin Latte"
                ),
            ),
            orderId = "123547",
            dateAndTime = "Jun 20, 2026 . 3:49 PM",
            address = "10th of Ramadan City",
            totalAmount = 95.00,
            statusCaption = DeliveryTimeline.DELIVERED.value
        )
    )


    orders.add(
        CurrentOrder(
            orderItems = listOf(
                OrderItems(
                    imageId = R.drawable.drink_1,
                    title = "Macchiato"
                ),

                OrderItems(
                    imageId = R.drawable.drink_2,
                    title = "Espresso"
                ),
                OrderItems(
                    imageId = R.drawable.drink_5,
                    title = "Cortado"
                ),
            ),
            orderId = "144231",
            dateAndTime = "Jun 20, 2026 . 5:49 PM",
            address = "10th of Ramadan City",
            totalAmount = 120.00,
            statusCaption = DeliveryTimeline.PREPARING.value
        )
    )


    orders.add(
        CurrentOrder(
            orderItems = listOf(
                OrderItems(
                    imageId = R.drawable.drink_4,
                    title = "Matcha Latte"
                ),

                OrderItems(
                    imageId = R.drawable.drink_5,
                    title = "Cortado"
                ),
            ),
            orderId = "463547",
            dateAndTime = "Jun 20, 2026 . 4:15 PM",
            address = "10th of Ramadan City",
            totalAmount = 60.00,
            statusCaption =  DeliveryTimeline.ON_THE_WAY.value,
        )
    )

    orders.add(
        CurrentOrder(
            orderItems = listOf(
                OrderItems(
                    imageId = R.drawable.drink_6,
                    title = "Latte Art"
                ),
            ),
            orderId = "113547",
            dateAndTime = "Jun 20, 2026 . 4.30 PM",
            address = "10th of Ramadan City",
            totalAmount = 40.00,
            statusCaption =  DeliveryTimeline.CANCELLED.value,
            status = ""
        )
    )
    return orders
}