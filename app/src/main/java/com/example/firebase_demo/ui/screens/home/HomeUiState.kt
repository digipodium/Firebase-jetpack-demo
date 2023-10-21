package com.example.firebase_demo.ui.screens.home

import com.example.firebase_demo.data.Electronic

data class HomeUiState(
    var electronic: List<Electronic>? = null,
    val name: String = "",
    val imgUrl: String = "",
    val price: String = "",
    val qty: Int = 0,
    val selectedElectronic: Electronic? = null
)
