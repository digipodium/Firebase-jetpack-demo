package com.example.firebase_demo.data

data class Electronic(
    val name: String = "",
    val imgUrl: String = "",
    val price: String = "",
    val qty: Int = 0,
    val createdOn: Long = System.currentTimeMillis()
)
