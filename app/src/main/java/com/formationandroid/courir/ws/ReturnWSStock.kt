package com.formationandroid.courir.ws

data class ReturnWSStock (
    val Id: Int,
    val IdModel: Int,
    val Model: ReturnWSModel,
    val Price: Float,
    val Quantity: Int,
    val ShoeSize: Int
)